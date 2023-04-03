package com.szalai.springwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private int age;
}
