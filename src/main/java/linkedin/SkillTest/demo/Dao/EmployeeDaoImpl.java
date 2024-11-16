package linkedin.SkillTest.demo.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import linkedin.SkillTest.demo.Model.Criteria;
import linkedin.SkillTest.demo.Model.CriteriaItem;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Autowired
	private EntityManagerFactory emFactory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ServiceResponse createEmployee(ORMEmployee emp) {
		ServiceResponse resp = new ServiceResponse();
		EntityManager manager = emFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		if (validateEmail(emp.getEmail()) == true) {
			resp.setResult("0");
			resp.setStatus(false);
			resp.setMessage("Email Already exists");
			return resp;
		}

		String serverDateTime = LocalDateTime.now().toString();
		emp.setModified_date(serverDateTime);

		try {
			transaction.begin();

			if (emp.getId() == null || emp.getId().toString().equals("")) {
				emp.setCreated_date(serverDateTime);
				emp.setPassword(passwordEncoder.encode(emp.getPassword()));
				manager.persist(emp);
				resp.setResult("1");
				resp.setStatus(true);
				resp.setMessage("saved");

			} else {
				emp.setPassword(passwordEncoder.encode(emp.getPassword()));
				manager.persist(emp);
				resp.setResult("1");
				resp.setStatus(true);
				resp.setMessage("updated");
			}
			transaction.commit();
			return resp;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			resp.setResult("0");
			resp.setStatus(false);
			resp.setMessage("Error saving data: " + e.getMessage());
			return resp;
		} finally {
			manager.close();
		}
	}

	// search by name, age or id
	@Override
	public List<GetEmployee> getEmployeeByCriteria(Criteria criteriaList) {
		EntityManager manager = emFactory.createEntityManager();

		String query = "";
		String pageIndex = "";
		String pageSize = "";

		for (CriteriaItem criteria : criteriaList.getCriteria()) {
			if (criteria.getParam_name() != null && criteria.getParam_value() != null) {
				switch (criteria.getParam_name()) {
				case "id":
					if (criteria.getParam_value() != null && !criteria.getParam_value().isEmpty()) {
						String id = criteria.getParam_value();
						query += " and id = '" + id + "'";
					}

					break;
				case "name":
					if (criteria.getParam_value() != null && !criteria.getParam_value().toString().equals("")) {

						String name = criteria.getParam_value();
						name = name.replace(" ", "%");
						query += " and e.name like '%" + name + "%' ";
					}
					break;
				case "age":
					if (criteria.getParam_value() != null && !criteria.getParam_value().toString().equals("")) {
						String age = criteria.getParam_value();
						query += " and e.age = '" + age + "' ";
					}
					break;
				case "pageIndex":

					if (criteria.getParam_value() != null && !criteria.getParam_value().isEmpty()) {
						pageIndex = criteria.getParam_value();
					} else {
						pageIndex = "1";
					}
					break;
				case "pageSize":

					if (criteria.getParam_value() != null && !criteria.getParam_value().isEmpty()) {
						pageSize = criteria.getParam_value();
					} else {
						pageSize = "10";
					}
					break;
				case "email":
					if (criteria.getParam_value() != null && !criteria.getParam_value().isEmpty()) {
						String email = criteria.getParam_value();
						query += " and e.email = '" + email + "'";
					}

					break;
				default:
					break;
				}
			}
		}

		StoredProcedureQuery sp = manager.createStoredProcedureQuery("spSearchEmployee", GetEmployee.class);
//		Before using this function make sure to execute this procedure:

//			create procedure spSearchEmployee @query varchar(200), @pageIndex varchar(100), @pageSize varchar(100) 
//			as begin
//			    set transaction isolation level read uncommitted;
//
//			    declare @sql nvarchar(max); 
//			    set @sql = N'select e.id, r.role_id, r.name as role_name, e.age, e.name, e.emp_class, e.subjects, e.attendance, isnull(e.email,'') email,
//			                    convert(date, e.client_date_created) as client_date_created
//			                from employee e
//			                join roles r on r.role_id = e.role_id  -- will return only records with a role (either admin or user)
//							where isnull(e.deleted, 0) <> 1
//			                ' + @query + 
//			                N' order by e.client_date_created desc  -- to sort the records by getting new records on top
//
//			                -- for pagination
//			                OFFSET ((cast(@pageIndex as int) - 1) * cast(@pageSize as int)) ROWS
//			                FETCH NEXT cast(@pageSize as int) ROWS ONLY;'; 
//
//			    print @sql;
//
//			    EXEC sp_executesql 
//			        @sql, 
//			        N'@pageIndex INT, @pageSize INT', 
//			        @pageIndex, 
//			        @pageSize;
//			end

		sp.registerStoredProcedureParameter("query", String.class, ParameterMode.IN);
		sp.setParameter("query", query);

		sp.registerStoredProcedureParameter("pageIndex", String.class, ParameterMode.IN);
		sp.setParameter("pageIndex", pageIndex);

		sp.registerStoredProcedureParameter("pageSize", String.class, ParameterMode.IN);
		sp.setParameter("pageSize", pageSize);

		try {
			logger.info("Executing stored procedure: \nspSearchEmployee {}, {}, {}", query, pageIndex, pageSize);
			sp.execute();
			List<GetEmployee> result = sp.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error getting data: " + e.getMessage());
		} finally {
			manager.close();
		}

	}

	private boolean validateEmail(String email) {
		EntityManager manager = emFactory.createEntityManager();
		String sql = "select count(*) from employee  where email = '" + email + "' and isnull(deleted, 0) <> 1";

		try {
			Query query = manager.createNativeQuery(sql);
			Integer count = (Integer) query.getSingleResult();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
	}

}
