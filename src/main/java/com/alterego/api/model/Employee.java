package com.alterego.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Date dob;
    private String status;
    private Double salary;
    private Integer speed;
}
