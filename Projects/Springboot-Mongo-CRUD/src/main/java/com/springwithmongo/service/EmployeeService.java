package com.springwithmongo.service;

import java.util.List;
import java.util.Optional;

import com.springwithmongo.model.Employee;

public interface EmployeeService {

	void saveEmployee(Employee employee);

	List<Employee> getEmployee();

	Optional<Employee> getEmployeeById(Long id);

	void deleteEmployee(Long id);

	Employee updateEmployee(Employee employee, Long id);

}
