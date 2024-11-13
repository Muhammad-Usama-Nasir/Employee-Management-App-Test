package linkedin.SkillTest.demo.Dao;

import java.util.List;

import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;

public interface EmployeeDao {

    ServiceResponse createEmployee(ORMEmployee emp);

    List<GetEmployee> getEmployeeById(String emp_id, String pageIndex, String pageSize);

    List<GetEmployee> getEmployeeByName(String name, String pageIndex, String pageSize);

    List<GetEmployee> getEmployeeByAge(String age, String pageIndex, String pageSize);

}
