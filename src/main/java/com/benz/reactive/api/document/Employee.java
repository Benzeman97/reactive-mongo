package com.benz.reactive.api.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@Getter
@Setter
public class Employee {

    private int _id;
    private String ename;
    private double salary;
}
