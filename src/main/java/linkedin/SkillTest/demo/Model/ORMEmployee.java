package linkedin.SkillTest.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class ORMEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long role_id;
    private Long age;
    private String name;
    private String emp_class;
    private String subjects;
    private String attendance;

    private String created_date;
    private String modified_date;
    private String client_date_created;  // to get the client date 
    private String client_date_modified; // // to get the client date
    private String system_ip;
    private Boolean deleted;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getCreated_date() {
        return created_date;
    }
    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
    public String getModified_date() {
        return modified_date;
    }
    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
    public String getClient_date_created() {
        return client_date_created;
    }
    public void setClient_date_created(String client_date_created) {
        this.client_date_created = client_date_created;
    }
    public String getClient_date_modified() {
        return client_date_modified;
    }
    public void setClient_date_modified(String client_date_modified) {
        this.client_date_modified = client_date_modified;
    }
    public String getSystem_ip() {
        return system_ip;
    }
    public void setSystem_ip(String system_ip) {
        this.system_ip = system_ip;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public String getEmp_class() {
        return emp_class;
    }
    public void setEmp_class(String emp_class) {
        this.emp_class = emp_class;
    }
    public Long getRole_id() {
        return role_id;
    }
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
    
    
}
