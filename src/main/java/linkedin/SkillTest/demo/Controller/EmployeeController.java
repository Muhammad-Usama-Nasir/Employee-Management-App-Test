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

    @GetMapping("/getEmployeeById/{emp_id}/{pageIndex}/{pageSize}")
    public @ResponseBody ResponseEntity<List<GetEmployee>> getEmployeeById(
        @PathVariable(value = "emp_id") String emp_id,
        @PathVariable(value = "pageIndex") String pageIndex,
        @PathVariable(value = "pageSize") String pageSize
        ) {
        List<GetEmployee> resp = empService.getEmployeeById(emp_id, pageIndex, pageSize);
        return new ResponseEntity<List<GetEmployee>>(resp, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByName/{name}/{pageIndex}/{pageSize}")
    public @ResponseBody ResponseEntity<List<GetEmployee>> getEmployeeByName(
        @PathVariable(value = "name") String name,
        @PathVariable(value = "pageIndex") String pageIndex,
        @PathVariable(value = "pageSize") String pageSize
        ) {
        List<GetEmployee> resp = empService.getEmployeeByName(name, pageIndex, pageSize);
        return new ResponseEntity<List<GetEmployee>>(resp, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByAge/{age}/{pageIndex}/{pageSize}")
    public @ResponseBody ResponseEntity<List<GetEmployee>> getEmployeeByAge(
        @PathVariable(value = "age") String age,
        @PathVariable(value = "pageIndex") String pageIndex,
        @PathVariable(value = "pageSize") String pageSize
        ) {
        List<GetEmployee> resp = empService.getEmployeeByAge(age, pageIndex, pageSize);
        return new ResponseEntity<List<GetEmployee>>(resp, HttpStatus.OK);
    }
}
