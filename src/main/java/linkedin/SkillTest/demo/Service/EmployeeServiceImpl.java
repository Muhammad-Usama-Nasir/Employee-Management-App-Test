package linkedin.SkillTest.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.SkillTest.demo.Dao.EmployeeDao;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public ServiceResponse createEmployee(ORMEmployee emp) {
        return employeeDao.createEmployee(emp);
    }

    @Override
    public List<GetEmployee> getEmployeeById(String emp_id, String pageIndex, String pageSize) {
        return employeeDao.getEmployeeById(emp_id, pageIndex, pageSize);
    }

    @Override
    public List<GetEmployee> getEmployeeByName(String name, String pageIndex, String pageSize) {
        return employeeDao.getEmployeeByName(name, pageIndex, pageSize);
    }

    @Override
    public List<GetEmployee> getEmployeeByAge(String age, String pageIndex, String pageSize) {
        return employeeDao.getEmployeeByAge(age, pageIndex, pageSize);
    }

}
