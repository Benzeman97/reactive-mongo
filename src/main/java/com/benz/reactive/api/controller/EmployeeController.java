package com.benz.reactive.api.controller;

import com.benz.reactive.api.dao.EmployeeDAO;
import com.benz.reactive.api.document.Employee;
import com.benz.reactive.api.model.EmployeeEvent;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private EmployeeDAO employeeDAO;

    public EmployeeController(EmployeeDAO employeeDAO)
    {
        this.employeeDAO=employeeDAO;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Employee> getAllEmployees()
    {
        return employeeDAO.findAll();
    }

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Employee> findEmployee(@PathVariable int id)
    {
        return employeeDAO.findById(id);
    }

    @GetMapping(value = "/{id}/event",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<EmployeeEvent> getEventEmplyee(@PathVariable int id)
    {
         return employeeDAO.findById(id)
                  .flatMapMany(emp->{
                     Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

                    Flux<EmployeeEvent> employeeFlux =
                            Flux.fromStream(Stream.of(new EmployeeEvent(emp,new Date()),new EmployeeEvent(emp,new Date()),
                                    new EmployeeEvent(emp,new Date())));

                    return Flux.zip(interval,employeeFlux).map(e->e.getT2());

                  });
    }

    @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Employee> saveEmployee(@RequestBody Employee employee)
    {
          return employeeDAO.save(employee);
    }

    @PutMapping(value = "/{id}/update",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee employee) throws Exception
    {
                return employeeDAO.findById(id)
                        .switchIfEmpty(Mono.error(new Exception("Employee is not found")))
                        .flatMap(e->{
                            e.setEname(employee.getEname());
                            e.setSalary(employee.getSalary());
                            return employeeDAO.save(e);
                        });

    }

    @DeleteMapping(value="/{id}")
    public Mono<Void> deleteEmployee(@PathVariable int id) throws Exception
    {
        return employeeDAO.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Employee is not found")))
                .flatMap(e->employeeDAO.delete(e));
    }
}
