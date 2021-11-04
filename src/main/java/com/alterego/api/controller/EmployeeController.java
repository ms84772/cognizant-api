package com.alterego.api.controller;


import com.alterego.api.model.Employee;
import com.alterego.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployeeById(id);
        ResponseEntity responseEntity ;
        if(employee!=null){
            responseEntity = new ResponseEntity(employee, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity("Employee not found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity getAllEmployee(){
        List<Employee> employeeList = employeeService.getAllEmployee();
        ResponseEntity responseEntity ;
        if(employeeList!=null){
            responseEntity = new ResponseEntity(employeeList, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity("Employee not found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody Employee employee){
        Employee employeeRs = employeeService.createEmployee(employee);
        ResponseEntity responseEntity;
        if(employeeRs!=null){
            responseEntity = new ResponseEntity(employeeRs, HttpStatus.CREATED);
        } else{
            responseEntity = new ResponseEntity("Bad request, no employee created ", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity updateEmployee(@RequestBody Employee employee, @PathVariable int id){
        Employee employeeRs = employeeService.updateEmployee(employee);
        ResponseEntity responseEntity;
        if(employeeRs!=null){
            responseEntity = new ResponseEntity(employeeRs, HttpStatus.CREATED);
        } else{
            responseEntity = new ResponseEntity("Bad request, no employee created ", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee( @PathVariable int id){
        boolean employeeRs = employeeService.deleteEmployee(id);
        ResponseEntity responseEntity;
        if(employeeRs){
            responseEntity = new ResponseEntity(String.format("Employee deleted: %s",id), HttpStatus.CREATED);
        } else{
            responseEntity = new ResponseEntity("Bad request, no employee created ", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
