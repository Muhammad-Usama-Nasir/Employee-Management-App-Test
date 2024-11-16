package linkedin.SkillTest.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import linkedin.SkillTest.demo.Model.ORMEmployee;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EntityManagerFactory emfactory;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user by database
		EntityManager manager = emfactory.createEntityManager();

		System.out.println(username);
		String sql = "select id, email, password, role, age, name, emp_class, subjects, attendance, created_date, modified_date, client_date_created,\n"
				+ "	 client_date_modified, system_ip, deleted from employee where isnull(deleted,0)<> 1 and email = '"
				+ username + "'";
		System.out.println(sql);
		try {
			Query query = manager.createNativeQuery(sql, ORMEmployee.class);
			ORMEmployee result = (ORMEmployee) query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new RuntimeException("User not found");
		} finally {
			manager.close();
		}

	}

}
