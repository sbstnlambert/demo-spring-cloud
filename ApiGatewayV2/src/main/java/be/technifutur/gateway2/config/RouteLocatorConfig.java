package be.technifutur.gateway2.config;

import be.technifutur.gateway2.predicate.NumberOfParamsRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    // /client/** -> client service
    // /movie/** -> movie service
//    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, NumberOfParamsRoutePredicateFactory predicateFactory) {
        return builder.routes()
                .route("to-client",
                        r -> r.path("/client/**")
                                .and()
                                .method("GET")
                                .filters(f ->
                                        f.rewritePath("/client/(?<path>.*)", "/$\\{path}")
                                                .addRequestParameter("my-param", "its-value")
                                                .retry(3)
                                )
                                .uri("lb://client-service")
                )
                .route("to-movie",
                        r -> r.path("/movie/**")
                                .filters(f -> f.rewritePath("/movie/(?<path>.*)", "/$\\{path}"))
                                .uri("lb://movie-service")
                )
                .route("word",
                        r -> r.path("/word")
                                .and()
                                .method("GET")
                                .and()
                                .predicate(predicateFactory.apply(new NumberOfParamsRoutePredicateFactory.Config(0)))
                                .filters(req -> req.addRequestParameter("word", "gateway"))
                                .uri("lb://movie-service")
                )
                .build();
    }

}
