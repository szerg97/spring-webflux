package com.szalai.springwebflux.service;

import com.szalai.springwebflux.dto.EmployeeDto;
import com.szalai.springwebflux.model.Employee;
import com.szalai.springwebflux.repository.EmployeeRepository;
import com.szalai.springwebflux.util.EmployeeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Flux<EmployeeDto> getEmployees(){
        return repository
                .findAll()
                .map(EmployeeConverter::entityToDto);
    }

    public Flux<EmployeeDto> getEmployeesByAgeBetween(
            int min,
            int max
    ){
        return repository
                .findByAgeBetween(min, max)
                .map(EmployeeConverter::entityToDto);
    }

    public Mono<EmployeeDto> getEmployee(String id){
        return repository
                .findById(id)
                .map(EmployeeConverter::entityToDto);
    }

    public Mono<EmployeeDto> saveEmployee(
            Mono<EmployeeDto> dtoMono
    ){
        return dtoMono
                .map(EmployeeConverter::dtoToEntity)
                .flatMap(repository::insert)
                .map(EmployeeConverter::entityToDto);
    }

    public Mono<EmployeeDto> updateEmployee(
            String id,
            Mono<EmployeeDto> dtoMono
    ){
        return repository
                .findById(id)
                .flatMap(e -> dtoMono
                        .map(EmployeeConverter::dtoToEntity)
                        .doOnNext(f -> f.setId(id)))
                .flatMap(repository::save)
                .map(EmployeeConverter::entityToDto);
    }

    public Mono<Void> deleteEmployee(String id){
        return repository.deleteById(id);
    }
}
