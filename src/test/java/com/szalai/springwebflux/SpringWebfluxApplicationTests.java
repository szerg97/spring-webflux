package com.szalai.springwebflux;

import com.szalai.springwebflux.controller.EmployeeController;
import com.szalai.springwebflux.dto.EmployeeDto;
import com.szalai.springwebflux.model.Employee;
import com.szalai.springwebflux.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
class SpringWebfluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService service;

    @Test
    public void addEmployeeTest(){
        Mono<EmployeeDto> dtoMono = Mono.just(
                new EmployeeDto(
                        "1234abcd",
                        "Test Employee",
                        "+3610000000",
                        30));
        when(service.saveEmployee(dtoMono)).thenReturn(dtoMono);
        webTestClient.post()
                .uri("/api/v1/employees")
                .body(
                        Mono.just(dtoMono),
                        EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getProductsTest(){
        Flux<EmployeeDto> dtoFlux = Flux.just(
                new EmployeeDto("1234abcd",
                        "Test Employee 1",
                        "+3610000000",
                        30),
                new EmployeeDto("1234abcde",
                        "Test Employee 2",
                        "+3610000001",
                        64),
                new EmployeeDto("1234abcdef",
                        "Test Employee 3",
                        "+3610000002",
                        24)
        );
        when(service.getEmployees()).thenReturn(dtoFlux);
        Flux<EmployeeDto> responseBody = webTestClient.get()
                .uri("/api/v1/employees")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto("1234abcd",
                        "Test Employee 1",
                        "+3610000000",
                        30))
                .expectNext(new EmployeeDto("1234abcde",
                        "Test Employee 2",
                        "+3610000001",
                        64))
                .expectNext(new EmployeeDto("1234abcdef",
                        "Test Employee 3",
                        "+3610000002",
                        24))
                .verifyComplete();
    }

    @Test
    public void getProductsByAgeBetweenTest(){
        Flux<EmployeeDto> dtoFlux = Flux.just(
                new EmployeeDto("1234abcd",
                        "Test Employee 1",
                        "+3610000000",
                        30),
                new EmployeeDto("1234abcdef",
                        "Test Employee 3",
                        "+3610000002",
                        24)
        );
        when(service.getEmployeesByAgeBetween(anyInt(), anyInt())).thenReturn(dtoFlux);
        Flux<EmployeeDto> responseBody = webTestClient.get()
                .uri("/api/v1/employees/by-age-between?min=20&max=50")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto("1234abcd",
                        "Test Employee 1",
                        "+3610000000",
                        30))
                .expectNext(new EmployeeDto("1234abcdef",
                        "Test Employee 3",
                        "+3610000002",
                        24))
                .verifyComplete();
    }

    @Test
    public void getEmployeeTest(){
        String id = "1234abcd";
        Mono<EmployeeDto> dtoMono = Mono.just(
                new EmployeeDto(
                        id,
                        "Test Employee",
                        "+3610000000",
                        30));
        when(service.getEmployee(id)).thenReturn(dtoMono);
        Mono<EmployeeDto> responseBody = webTestClient.get()
                .uri("/api/v1/employees/1234abcd")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody()
                .next();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(e -> e.getId().equals(id))
                .verifyComplete();
    }

    @Test
    public void updateEmployeeTest(){
        String id = "1234abcd";
        Mono<EmployeeDto> dtoMono = Mono.just(
                new EmployeeDto(
                        null,
                        "Test Employee",
                        "+3610000000",
                        30));
        when(service.updateEmployee(id, dtoMono)).thenReturn(dtoMono);
        webTestClient.put()
                .uri("/api/v1/employees/" + id)
                .body(
                        Mono.just(dtoMono),
                        EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteEmployee(){
        given(service.deleteEmployee(any())).willReturn(Mono.empty());
        webTestClient.delete()
                .uri("/api/v1/employees/1234abcd")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
