package linkedin.SkillTest.demo.Model;

public enum Role {

	Admin("Admin"), Employee("Employee");

	private final String roleName;

	Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

}
