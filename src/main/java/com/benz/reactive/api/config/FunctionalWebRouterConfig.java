package com.benz.reactive.api.config;

import com.benz.reactive.api.handler.FunctionalWebHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FunctionalWebRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(FunctionalWebHandler functionalWebHandler)
    {
       return RouterFunctions.route(RequestPredicates.GET("/functional/flux")
        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),req->functionalWebHandler.returnFlux(req))
                .andRoute(RequestPredicates.GET("/functional/mono").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        req->functionalWebHandler.returnMono(req));

    }
}
