package linkedin.SkillTest.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class MyConfig {

	// Creating In-Memory-User

//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails userDetails = User.builder().
//				username("usama@test.com").
//				password(passwordEncoder().encode("usama123")).
//				roles("ADMIN").
//				build();
//		
//		UserDetails adminDetails = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(userDetails, adminDetails);
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

}