package com.benz.reactive.api.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FunctionalWebHandler {

    public Mono<ServerResponse> returnFlux(ServerRequest serverRequest)
    {
         return ServerResponse.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(
                         Flux.just(65,43,23,56,54,21).log(),
                         Integer.class
                 );
    }

    public Mono<ServerResponse> returnMono(ServerRequest serverRequest)
    {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(763).log(),
                        Integer.class
                );
    }
}
