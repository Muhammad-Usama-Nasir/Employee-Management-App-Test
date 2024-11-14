package linkedin.SkillTest.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import linkedin.SkillTest.demo.Model.Criteria;
import linkedin.SkillTest.demo.Model.GetEmployee;
import linkedin.SkillTest.demo.Model.ORMEmployee;
import linkedin.SkillTest.demo.Model.ServiceResponse;
import linkedin.SkillTest.demo.Service.EmployeeService;

@SpringBootApplication
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping("/createEmployee")
    public @ResponseBody ResponseEntity<ServiceResponse> createEmployee(@RequestBody ORMEmployee emp) {
        ServiceResponse resp = empService.createEmployee(emp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByCriteria")
    public @ResponseBody ResponseEntity<List<GetEmployee>> getEmployeeByCriteria(
        @RequestBody Criteria criteria) {
        List<GetEmployee> resp = empService.getEmployeeByCriteria(criteria);
        return new ResponseEntity<List<GetEmployee>>(resp, HttpStatus.OK);
    }

}
