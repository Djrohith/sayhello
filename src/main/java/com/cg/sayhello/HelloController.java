package com.cg.sayhello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.sayhello.model.Employee;

/**
 * @author balmani
 *
 */
@RestController
public class HelloController {

  @GetMapping("/hello")
  String sayHello() {

    return "[\"message\":\"Say Hello to <strong>Rohit</strong>\"]";

  }

  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  public Employee firstPage() {

    Employee emp = new Employee();
    emp.setName("rohit");
    emp.setDesignation("manager");
    emp.setEmpId("1");
    emp.setSalary(300000);

    return emp;
  }

}
