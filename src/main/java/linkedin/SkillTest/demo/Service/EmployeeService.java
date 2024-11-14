package linkedin.SkillTest.demo.Service;

import java.util.List;

import linkedin.SkillTest.demo.Model.Criteria;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;

public interface EmployeeService {

    ServiceResponse createEmployee(ORMEmployee emp);

    List<GetEmployee> getEmployeeByCriteria(Criteria criteria);


}
