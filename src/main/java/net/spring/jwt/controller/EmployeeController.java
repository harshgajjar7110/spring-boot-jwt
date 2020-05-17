package net.spring.jwt.controller;

import net.spring.jwt.Application;
import net.spring.jwt.model.AuthRequest;
import net.spring.jwt.model.AuthResponse;
import net.spring.jwt.model.Employee;
import net.spring.jwt.repository.EmployeeRepository;
import net.spring.jwt.service.EmployeeDetailsService;
import net.spring.jwt.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")

public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    String message = "Log Done";
    @Autowired
    EmployeeDetailsService employeeDetailsService;
    @Autowired
    JWTUtil authManager;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        log.info("Customers found with findAll():");
        log.info("-------------------------------");


        return employeeRepository.findAll();

    }

    @PostMapping("auth")
    public ResponseEntity<?> getauth(@RequestBody AuthRequest auth)
            throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("sorry");
        }
        final UserDetails userDetails = employeeDetailsService.loadUserByUsername(auth.getUsername());

        return ResponseEntity.ok(new AuthResponse(authManager.generateToken(userDetails)));

    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);

    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") String employeeId)
            {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        return Objects.isNull(employee) ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("") : ResponseEntity.ok().body(employee);
    }


    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") String employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) {

        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        employeeRepository.save(employee);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") String employeeId)
             {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.OK);
        //response;
    }

}