package com.alterego.api.controller;


import com.alterego.api.model.Employee;
import com.alterego.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @PostMapping("/move-employees")
    public ResponseEntity moveEmployeesFromSiteAtoB(@RequestBody List<Employee> employeeList){

        ResponseEntity responseEntity;
        Map response = new HashMap();
        if(employeeList!=null && employeeList.size()==0 ||employeeList.size() == 1){
            response.put("Error", "Employee list has to be more than 2 items");
            responseEntity = new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }else{
            List<Employee> empListSorted = employeeList
                    .stream()
                    .sorted(Comparator.comparingInt(Employee::getSpeed).reversed())
                    .collect(Collectors.toList());
            int trip=1;
            int timeSpeed=0;
            for(int i=0;i<empListSorted.size();i++){
                List<Employee> tripList = new ArrayList<>();
                Employee e1 = empListSorted.get(i);
                tripList.add(e1);
                timeSpeed += e1.getSpeed();
                if(i+1< empListSorted.size()){
                    Employee e2 = empListSorted.get(i+1);
                    tripList.add(e2);
                }else{
                    List<Employee> tripBack = new ArrayList<>();
                    List<Employee> lastTrip = (List<Employee>)response.get("Trip "+(trip-1));
                    Employee empBack = lastTrip.get(lastTrip.size()-1);
                    tripBack.add(empBack);
                    trip++;
                    response.put("Trip Back "+trip,tripBack);
                    tripList.add(empBack);
                    timeSpeed += empBack.getSpeed();
                    timeSpeed += e1.getSpeed();
                    trip++;
                }
                response.put("Trip "+trip,tripList);
                trip++;
                i++;
            }
            response.put("TimeSpeed",timeSpeed);
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }
        return responseEntity;
    }

}
