package com.szalai.springwebflux.controller;

import com.szalai.springwebflux.dto.EmployeeDto;
import com.szalai.springwebflux.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public Flux<EmployeeDto> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/by-age-between")
    public Flux<EmployeeDto> getEmployeesByAgeBetween(
            @RequestParam int min,
            @RequestParam int max
    ){
        return employeeService.getEmployeesByAgeBetween(min, max);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(
            @PathVariable String id
    ){
        return employeeService.getEmployee(id);
    }

    @PostMapping("")
    public Mono<EmployeeDto> saveEmployee(
            @RequestBody Mono<EmployeeDto> dtoMono
    ){
        return employeeService.saveEmployee(dtoMono);
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(
            @PathVariable String id,
            @RequestBody Mono<EmployeeDto> dtoMono
    ){
        return employeeService.updateEmployee(id, dtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteEmployee(
            @PathVariable String id
    ){
        return employeeService.deleteEmployee(id);
    }
}
