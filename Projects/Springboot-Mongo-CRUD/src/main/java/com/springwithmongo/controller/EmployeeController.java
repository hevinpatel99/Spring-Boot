package com.springwithmongo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springwithmongo.model.Employee;
import com.springwithmongo.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/saveEmployee")
	public String saveEmployee(@RequestBody Employee employee) {
		this.employeeService.saveEmployee(employee);
		return "Employee Added Successfully...";
	}

	@GetMapping("/getAllEmployee")
	public List<Employee> getEmployee() {

		return employeeService.getEmployee();
	}

	@GetMapping("/getEmployeeById/{id}")
	public Optional<Employee> getEmployee(@PathVariable Long id) {

		return employeeService.getEmployeeById(id);
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		this.employeeService.deleteEmployee(id);
		return "Employee Deleted Successfully...";
	}
	
	@PutMapping("/updateEmployee/{id}")
	public Employee updateEmployee(@RequestBody Employee employee,@PathVariable Long id) {
		Employee emp = this.employeeService.updateEmployee(employee,id);
		return emp;
	}
}
