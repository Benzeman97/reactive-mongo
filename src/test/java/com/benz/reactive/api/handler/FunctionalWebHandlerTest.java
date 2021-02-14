package com.benz.reactive.api.handler;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class FunctionalWebHandlerTest {

    @Autowired
      private WebTestClient webTestClient;

    @Test
    @DisplayName("FluxTest_1")
    public void fluxTest_1()
    {
        List<Integer> expectedEntityExchangeResult
                =new ArrayList<>(Arrays.asList(65,43,23,56,54,21));

       EntityExchangeResult<List<Integer>> actualEntityResult = webTestClient.get().uri("http://127.0.0.1:9090/functional/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
               .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        Assertions.assertEquals(expectedEntityExchangeResult,actualEntityResult.getResponseBody());
    }

    @Test
    @DisplayName("FluxTest_2")
    public void fluxTest_2()
    {

     Flux<Integer> integerFlux = webTestClient.get().uri("http://127.0.0.1:9090/functional/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(65,43,23,56,54,21)
                .verifyComplete();

    }

    @Test
    @DisplayName("MonoTest")
    public void monoTest()
    {
        int expectedValue = 763;

         webTestClient.get().uri("http://127.0.0.1:9090/functional/mono")
                 .accept(MediaType.APPLICATION_JSON)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody(Integer.class)
                 .consumeWith(res->{
           Assertions.assertEquals(expectedValue,res.getResponseBody(),String.format("Expected %d but was %d",expectedValue,res.getResponseBody()));
                 });
    }

}
