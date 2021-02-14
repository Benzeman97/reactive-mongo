package com.benz.reactive.api.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> returnFlux()
    {
        return Flux.just(1,3,5,3,2,6)
                .log();
    }

    @GetMapping(value="/fluxStream",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<Integer> returnFluxStream()
    {
        return Flux.just(1,3,5,3,2,6)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/streamInfinite",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<Long> infiniteStream()
    {
         return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping("/mono")
    public Mono<Integer> returnMono()
    {
        return Mono.just(889).log();
    }
}
