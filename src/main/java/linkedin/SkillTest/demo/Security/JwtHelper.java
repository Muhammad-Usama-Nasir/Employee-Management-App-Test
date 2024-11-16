package linkedin.SkillTest.demo.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	// Time for token
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	// secret key
	private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	private String doGenerateToken(Map<String, Object> claims, String subject, List<String> roles) {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		claims.put("Role", roles);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	// Generate token for user including roles
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());
		return doGenerateToken(claims, userDetails.getUsername(), roles);
	}

	public List<String> getRolesFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (List<String>) claims.get("roles"); // Assuming roles are stored in the "roles" claim
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		List<String> roles = getRolesFromToken(token);
		// You can also validate the roles here, if necessary
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

}
