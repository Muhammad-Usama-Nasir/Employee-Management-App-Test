package linkedin.SkillTest.demo.Model;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @Builder
// @Getter
// @Setter
public class JwtResponse {

	private String jwtToken;
	private String username;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String jwtToken, String username) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
	}

	@Override
	public String toString() {
		return "JWTResponse [jwtToken=" + jwtToken + ", username=" + username + "]";
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static JWTResponseBuilder builder() {
		return new JWTResponseBuilder();
	}

	public static class JWTResponseBuilder {
		private String jwtToken;
		private String username;

		public JWTResponseBuilder jwtToken(String jwtToken) {
			this.jwtToken = jwtToken;
			return this;
		}

		public JWTResponseBuilder username(String username) {
			this.username = username;
			return this;
		}

		public JwtResponse build() {
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.jwtToken = this.jwtToken;
			jwtResponse.username = this.username;
			return jwtResponse;
		}
	}

}
