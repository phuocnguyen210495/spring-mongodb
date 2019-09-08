package com.techfinally.example.repository;

import com.techfinally.example.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tech Finally
 */
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>{
    
}
