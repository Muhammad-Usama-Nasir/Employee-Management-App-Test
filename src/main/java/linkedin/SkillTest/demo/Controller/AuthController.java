package linkedin.SkillTest.demo.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import linkedin.SkillTest.demo.Dao.EmployeeDaoImpl;
import linkedin.SkillTest.demo.Model.JwtRequest;
import linkedin.SkillTest.demo.Model.JwtResponse;
import linkedin.SkillTest.demo.Model.ORMCheckMailQuery;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;
import linkedin.SkillTest.demo.Security.JwtHelper;
import linkedin.SkillTest.demo.Service.EmployeeService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private EmployeeDaoImpl employeedao;

	@Autowired
	private EntityManagerFactory emFactory;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	@Autowired
	PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

	@PostMapping("/signup")
	public @ResponseBody ResponseEntity<ServiceResponse> createEmployee(@RequestBody ORMEmployee emp) {

		ServiceResponse resp = employeeService.createEmployee(emp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

//	@PostMapping("/verify")
//	public @ResponseBody ResponseEntity<ServiceResponse> verifyAccount(@RequestParam("code") String code) {
//
//		ServiceResponse resp = employeeService.verifyAccount(code);
//		return new ResponseEntity<>(resp, HttpStatus.OK);
//	}

	@GetMapping
	private Optional<ORMCheckMailQuery> getEmployeeMailetc(String email) {
		EntityManager manager = emFactory.createEntityManager();
		String sql = "select id, email, password from employee where email = :email";
		try {
			Query query = manager.createNativeQuery(sql, ORMCheckMailQuery.class);
			query.setParameter("email", email);
			Optional<ORMCheckMailQuery> result = (Optional<ORMCheckMailQuery>) query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	@PostMapping("confirm-verification")
	public String confirmVerification(@RequestBody JwtRequest verifyCredentials) {

		String email = verifyCredentials.getEmail();
		String password = verifyCredentials.getPassword();

		Optional<ORMCheckMailQuery> checkMail_query = getEmployeeMailetc(email);
//		String id = checkMail_query.get().getId().toString();

		if (!checkMail_query.isPresent()) {
			return "Invalid email!";
		}

		if (!passwordEncoder.matches(password, checkMail_query.get().getPassword())) {
			return "Invalid email or password!";
		}

//		if (!employee.getIsVerified()) {
//			return "Please verify your email before logging in.";
//		}

		return "Account Verified successfully...!!!";
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.helper.generateToken(userDetails);

		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}