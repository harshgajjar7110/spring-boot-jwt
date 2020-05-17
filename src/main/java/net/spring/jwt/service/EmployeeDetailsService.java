package net.spring.jwt.service;

import net.spring.jwt.model.Employee;
import net.spring.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee byEmailId = employeeRepository.findByEmailId(s);
       if(byEmailId != null){

        return new User(byEmailId.getEmailId(),byEmailId.getPassword(),new ArrayList<>());
       }else {
           throw  new UsernameNotFoundException("Not Found");
       }
    }
}
