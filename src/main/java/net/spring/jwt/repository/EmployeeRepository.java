package net.spring.jwt.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import net.spring.jwt.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
	List<Employee> findByFirstName(String first_name);
	Employee findByEmailId(String emailId);
	
} 
