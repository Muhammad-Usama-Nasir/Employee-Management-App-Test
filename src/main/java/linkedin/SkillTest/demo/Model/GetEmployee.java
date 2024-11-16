package linkedin.SkillTest.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GetEmployee {

	@Id
	private Long id;
	private String email;
	private String role;
	private String age;
	private String name;
	private String emp_class;
	private String subjects;
	private String attendance;

	private String client_date_created; // to get the client date

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmp_class() {
		return emp_class;
	}

	public void setEmp_class(String emp_class) {
		this.emp_class = emp_class;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getClient_date_created() {
		return client_date_created;
	}

	public void setClient_date_created(String client_date_created) {
		this.client_date_created = client_date_created;
	}

}
