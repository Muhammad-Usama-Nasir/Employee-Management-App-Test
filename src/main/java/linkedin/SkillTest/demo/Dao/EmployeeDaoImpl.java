package linkedin.SkillTest.demo.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import linkedin.SkillTest.demo.Model.Criteria;
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

    // search by name, age or id
    @Override
    public List<GetEmployee> getEmployeeByCriteria(Criteria criteria) {
        EntityManager manager = emFactory.createEntityManager();

        String query = "where isnull(e.deleted, 0) <> 1";
        String id = "";
        String name = "";
        String age = "";
        String pageIndex = "";
        String pageSize = "";

        if (criteria.getParam_name() != null && criteria.getParam_value() != null) {
            switch (criteria.getParam_name()) {
                case "id":
                    id = criteria.getParam_value();
                    break;
                case "name":
                    name = criteria.getParam_value();
                    break;
                case "age":
                    age = criteria.getParam_value();
                    break;
                case "pageIndex":
                    pageIndex = criteria.getParam_value();
                    break;
                case "pageSize":
                    pageSize = criteria.getParam_value();
                    break;
                default:
                    break;
            }
        }

        if (id != null && !id.equals("")) {
            query += " and e.id = '" + id + "' ";
        }
        if (name != null && !name.equals("")) {

            query += " and e.name like '%" + name + "%' ";
        }
        if (age != null && !age.equals("")) {
            query += " and e.age = '" + age + "' ";
        }
        if (pageIndex == null || pageIndex.equals("")) {
            pageIndex = "1";
        }
        if (pageSize == null || pageSize.equals("")) {
            pageIndex = "10";
        }

        StoredProcedureQuery sp = manager.createStoredProcedureQuery("spSearchEmployee", GetEmployee.class);

        sp.registerStoredProcedureParameter("pageIndex", String.class, ParameterMode.IN);
        sp.setParameter("pageIndex", pageIndex);

        sp.registerStoredProcedureParameter("pageSize", String.class, ParameterMode.IN);
        sp.setParameter("pageSize", pageSize);

        sp.registerStoredProcedureParameter("query", String.class, ParameterMode.IN);
        sp.setParameter("query", query);

        try {
            System.out.println("Query before: " + sp);
            sp.execute();
            System.out.println("Query after: " + query);
            List<GetEmployee> result = sp.getResultList();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting data: " + e.getMessage());
        } finally {
            manager.close();
        }

    }

}
