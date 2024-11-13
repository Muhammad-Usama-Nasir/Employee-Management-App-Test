package linkedin.SkillTest.demo.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;
import linkedin.SkillTest.demo.Repo.EmployeeRepo;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private EmployeeRepo empRepo;

    @Autowired
    private EntityManagerFactory emFactory;

    @Override
    public ServiceResponse createEmployee(ORMEmployee emp) {

        ServiceResponse resp = new ServiceResponse();
        String serverDateTime = LocalDateTime.now().toString();
        emp.setModified_date(serverDateTime);

        try {

            if (emp.getId() != null || emp.getId().toString().equals("")) {
                emp.setCreated_date(serverDateTime);
                empRepo.save(emp);
                resp.setResult("1");
                resp.setStatus(true);
                resp.setMessage("saved");

            } else {
                emp.setCreated_date(serverDateTime);
                empRepo.save(emp);
                resp.setResult("1");
                resp.setStatus(true);
                resp.setMessage("updated");
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving data");
        }
    }


    // To get Employee by id
    @Override
    public List<GetEmployee> getEmployeeById(String emp_id, String pageIndex, String pageSize) {
        EntityManager manager = emFactory.createEntityManager();

        StoredProcedureQuery sp = manager.createStoredProcedureQuery("spSearchEmployeeById", GetEmployee.class);
        
        // pageIndex and pageSize will be sent from the frontend
        sp.registerStoredProcedureParameter(pageIndex, String.class, ParameterMode.IN);
        sp.setParameter("pageIndex", pageIndex);

        sp.registerStoredProcedureParameter(emp_id, String.class, ParameterMode.IN);
        sp.setParameter("emp_id", emp_id);

        sp.registerStoredProcedureParameter(pageSize, String.class, ParameterMode.IN);
        sp.setParameter("pageSize", pageSize);
        sp.execute();

        List<GetEmployee> result = sp.getResultList();
        return result;

    }


    // To get Employee by name
    @Override
    public List<GetEmployee> getEmployeeByName(String name, String pageIndex, String pageSize) {
        EntityManager manager = emFactory.createEntityManager();

        StoredProcedureQuery sp = manager.createStoredProcedureQuery("spSearchEmployeeByName", GetEmployee.class);
        
        // pageIndex and pageSize will be sent from the frontend
        sp.registerStoredProcedureParameter(pageIndex, String.class, ParameterMode.IN);
        sp.setParameter("pageIndex", pageIndex);

        sp.registerStoredProcedureParameter(name, String.class, ParameterMode.IN);
        sp.setParameter("name", name);

        sp.registerStoredProcedureParameter(pageSize, String.class, ParameterMode.IN);
        sp.setParameter("pageSize", pageSize);
        sp.execute();

        List<GetEmployee> result = sp.getResultList();
        return result;

    }

    // To get Employee by age
    @Override
    public List<GetEmployee> getEmployeeByAge(String age, String pageIndex, String pageSize) {
        EntityManager manager = emFactory.createEntityManager();

        StoredProcedureQuery sp = manager.createStoredProcedureQuery("spSearchEmployeeByAge", GetEmployee.class);
        
        // pageIndex and pageSize will be sent from the frontend
        sp.registerStoredProcedureParameter(pageIndex, String.class, ParameterMode.IN);
        sp.setParameter("pageIndex", pageIndex);

        sp.registerStoredProcedureParameter(age, String.class, ParameterMode.IN);
        sp.setParameter("age", age);

        sp.registerStoredProcedureParameter(pageSize, String.class, ParameterMode.IN);
        sp.setParameter("pageSize", pageSize);
        sp.execute();

        List<GetEmployee> result = sp.getResultList();
        return result;

    }


}
