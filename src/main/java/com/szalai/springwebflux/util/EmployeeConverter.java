package com.szalai.springwebflux.util;

import com.szalai.springwebflux.dto.EmployeeDto;
import com.szalai.springwebflux.model.Employee;

public class EmployeeConverter {

    public static EmployeeDto entityToDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPhoneNumber(),
                employee.getAge()
        );
    }

    public static Employee dtoToEntity(EmployeeDto dto){
        return new Employee(
                dto.getId(),
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getAge()
        );
    }
}
