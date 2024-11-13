package linkedin.SkillTest.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import linkedin.SkillTest.demo.Model.ORMEmployee;

@Repository
public interface EmployeeRepo extends JpaRepository<ORMEmployee, Long>{


}
