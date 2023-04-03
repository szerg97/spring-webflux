package com.szalai.springwebflux.client;

import com.szalai.springwebflux.model.Employee;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeWebClient {

    WebClient client = WebClient.create("http://localhost:8080/api/v1");

    public void retrieveEmployee(){
        Mono<Employee> employeeMono = client.get()
                .uri("/employees/{id}")
                .retrieve()
                .bodyToMono(Employee.class);

        employeeMono.subscribe(System.out::println);
    }

    public void retrieveAllEmployees(){
        Flux<Employee> employeeFlux = client.get()
                .uri("/employees")
                .retrieve()
                .bodyToFlux(Employee.class);

        employeeFlux.subscribe(System.out::println);
    }
}
