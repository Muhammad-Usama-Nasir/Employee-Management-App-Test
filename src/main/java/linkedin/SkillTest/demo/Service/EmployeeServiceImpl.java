package linkedin.SkillTest.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.SkillTest.demo.Dao.EmployeeDao;
import linkedin.SkillTest.demo.Model.Criteria;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public ServiceResponse createEmployee(ORMEmployee emp) {
		return employeeDao.createEmployee(emp);
	}

	@Override
	public List<GetEmployee> getEmployeeByCriteria(Criteria criteriaList) {
		return employeeDao.getEmployeeByCriteria(criteriaList);
	}

}
