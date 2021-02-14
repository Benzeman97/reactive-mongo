package com.benz.reactive.api.dao;

import com.benz.reactive.api.document.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDAO extends ReactiveMongoRepository<Employee,Integer> {
}
