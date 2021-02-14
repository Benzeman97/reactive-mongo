package com.benz.reactive.api.model;

import com.benz.reactive.api.document.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeEvent {

    private Employee employee;
    private Date date;

}
