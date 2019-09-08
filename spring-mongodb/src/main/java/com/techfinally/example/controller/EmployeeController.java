package com.techfinally.example.controller;

import com.techfinally.example.bean.ResponseData;
import com.techfinally.example.model.Employee;
import com.techfinally.example.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tech Finally
 */
@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> findByAll() {
        List<Employee> list = employeeRepository.findAll();
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> findById(@PathVariable String id) {
        Optional<Employee> list = employeeRepository.findById(id);
        Employee employee = null;
        if (list != null && list.isPresent()) {
            employee = list.get();
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> create(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> update(@RequestBody Employee employee) {
        Optional<Employee> list = employeeRepository.findById(employee.getEmpNo());
        if (list != null && list.isPresent()) {
            employeeRepository.save(list.get());
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> delete(@PathVariable String id) {
        Optional<Employee> list = employeeRepository.findById(id);
        if (list != null && list.isPresent()) {
            employeeRepository.delete(list.get());
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
