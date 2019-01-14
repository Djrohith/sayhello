package com.cg.sayhello.model;

/**
 * @author balmani
 *
 */
public class Employee {
  private String empId;

  private String name;

  private String designation;

  private double salary;

  public Employee() {

  }

  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public String getDesignation() {

    return this.designation;
  }

  public void setDesignation(String designation) {

    this.designation = designation;
  }

  public double getSalary() {

    return this.salary;
  }

  public void setSalary(double salary) {

    this.salary = salary;
  }

  public String getEmpId() {

    return this.empId;
  }

  public void setEmpId(String empId) {

    this.empId = empId;
  }

}