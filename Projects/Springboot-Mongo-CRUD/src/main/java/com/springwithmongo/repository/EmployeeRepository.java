package com.springwithmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springwithmongo.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {

}
