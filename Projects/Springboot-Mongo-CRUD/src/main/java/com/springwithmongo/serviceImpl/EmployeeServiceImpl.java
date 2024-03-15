package com.springwithmongo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springwithmongo.model.Employee;
import com.springwithmongo.repository.EmployeeRepository;
import com.springwithmongo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getEmployee() {
		return employeeRepository.findAll(); 
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void deleteEmployee(Long id) {
	    this.employeeRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee, Long id) {
		Optional<Employee> empOptional = employeeRepository.findById(id);
		Employee emp = empOptional.get();
		emp.setName(employee.getName());
		emp.setSalary(employee.getSalary());
		emp.setAge(employee.getAge());
		this.employeeRepository.save(emp);
		return emp;
	}

}
