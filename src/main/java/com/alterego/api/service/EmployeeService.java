package com.alterego.api.service;

import com.alterego.api.model.Employee;
import com.alterego.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeById(int id){
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return employeeList;
    }

    public Employee createEmployee(Employee employee) {
        Employee employeeRs= employeeRepository.save(employee);
        return employeeRs;
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(int id) {
        Employee employee = new Employee();
        employee.setId(id);
        employeeRepository.delete(employee);
        return true;
    }
}
