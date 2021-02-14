package com.benz.reactive.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFluxTest
@ExtendWith(SpringExtension.class)
public class FluxAndMonoControllerTest {

    @Autowired
     private WebTestClient webTestClient;

        @Test
        @DisplayName("FluxTest_1")
        public void returnFluxTest_1()
        {
            Flux<Integer> integerFlux= webTestClient.get().uri("http://localhost:9090/flux")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk()
                     .returnResult(Integer.class)
                     .getResponseBody();

            StepVerifier.create(integerFlux)
                    .expectSubscription()
                    .expectNext(1,3,5,3,2,6)
                    .verifyComplete();

        }

    @Test
    @DisplayName("FluxTest_2")
    public void returnFluxTest_2()
    {
        Flux<Integer> integerFlux= webTestClient.get().uri("http://localhost:9090/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        integerFlux.subscribe(System.out::println,System.out::println,()-> System.out.println("Completed"));

    }

    @Test
    @DisplayName("FluxTest_3")
    public void returnFluxTest_3()
    {
        List<Integer> expectedEntityExchangeResult
                =new ArrayList<>(Arrays.asList(1,3,5,3,2,6));
        EntityExchangeResult<List<Integer>> entityExchangeResult=webTestClient.get().uri("http://localhost:9090/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        Assertions.assertEquals(expectedEntityExchangeResult,
                entityExchangeResult.getResponseBody());
    }

    @Test
    @DisplayName("FluxTest_4")
    public void returnFluxTest_4()
    {
         webTestClient.get().uri("http://127.0.0.1:9090/flux")
                 .accept(MediaType.APPLICATION_JSON)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(Integer.class)
                 .hasSize(6);
    }

    @Test
    @DisplayName("FluxTest_5")
    public void returnFluxTest_5()
    {
        List<Integer> expectedExchangeResult =
                new ArrayList<>(Arrays.asList(1,3,5,3,2,6));
         webTestClient.get().uri("http://127.0.0.1:9090/flux")
                 .accept(MediaType.APPLICATION_JSON)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(Integer.class)
                 .consumeWith(res->{
                     Assertions.assertEquals(expectedExchangeResult,
                             res.getResponseBody(),"completed");
                 });
    }

    @Test
    @DisplayName("FluxStream_1")
    public void returnFluxStream_1()
    {
         Flux<Integer> integerStream = webTestClient.get().uri("http://127.0.0.1/fluxStream")
                    .accept(MediaType.APPLICATION_STREAM_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(Integer.class)
                    .getResponseBody();

         StepVerifier.create(integerStream)
                 .expectSubscription()
                 .expectNext(1,3,5,3,2,6)
                 .verifyComplete();

       // integerStream.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
    }

    @Test
    @DisplayName("InfiniteStreamTest")
    public void infiniteStreamTest()
    {
         Flux<Long> infiniteStream=webTestClient.get().uri("http://localhost:9090/streamInfinite")
                 .accept(MediaType.APPLICATION_STREAM_JSON)
                 .exchange()
                 .expectStatus().isOk()
                 .returnResult(Long.class)
                 .getResponseBody();

         StepVerifier.create(infiniteStream)
                 .expectNext(0L,1L,2L,3L,4L,5L)
                 .thenCancel()
                 .verify();
    }

    @Test
    @DisplayName("MonoTest_1")
    public void monoTest_1()
    {
        int expectedValue=889;

         webTestClient.get().uri("http://localhost:9090/mono")
                 .accept(MediaType.APPLICATION_JSON)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody(Integer.class)
                 .consumeWith(res->{
                     Assertions.assertEquals(expectedValue,res.getResponseBody(),String.format("Expected %d but actual was %d",expectedValue,res.getResponseBody()));
                 });
    }


}
