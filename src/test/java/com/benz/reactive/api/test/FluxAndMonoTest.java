package com.benz.reactive.api.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Consumer;

@SpringBootTest
public class FluxAndMonoTest {


     @Test
     @DisplayName(("FluxTest"))
    public void fluxTest()
    {
       Flux<String> stringProducer = Flux.just("Spring","Spring Boot","Reactive Spring")
               // .concatWith(Flux.error(new RuntimeException("Error is occurred")))
                .concatWith(Flux.just("Programming"))
                .log();

        stringProducer.subscribe(System.out::println,System.err::println,()->System.out.println("Completed"));
    }

    @Test
    @DisplayName("FluxWithoutError")
    public void fluxTest_withoutError()
    {
        Flux<String> stringProducer= Flux.just("Kelly Brook","Nafaz Benzema","Doto Kama")
                .log();

        StepVerifier.create(stringProducer).expectSubscription()
                .expectNext("Kelly Brook")
                .expectNext("Nafaz Benzema")
                .expectNext("Doto Kama")
                .verifyComplete();
    }

    @Test
    @DisplayName("FluxWithError")
    public void fluxTest_withError()
    {
        Flux<String> stringProducer= Flux.just("Kelly Brook","Nafaz Benzema","Doto Kama")
                .concatWith(Flux.error(new RuntimeException("Error is occurred")))
                .log();

        StepVerifier.create(stringProducer)
                .expectSubscription()
                .expectNext("Kelly Brook")
                .expectNext("Nafaz Benzema")
                .expectNext("Doto Kama")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("MonoTestWithoutError")
    public void monoTest_withoutError()
    {
        Mono<String> stringMono = Mono.just("Spring Boot").log();

        StepVerifier.create(stringMono)
                .expectNext("Spring Boot")
                .verifyComplete();
    }

    @Test
    @DisplayName("MonoTestWithError")
    public void monoTest_withError()
    {
       Mono<String> stringMono = Mono.error(new RuntimeException("Exception is occurred"));

       StepVerifier.create(stringMono.log())
               .expectError(RuntimeException.class)
               .verify();

    }

}
