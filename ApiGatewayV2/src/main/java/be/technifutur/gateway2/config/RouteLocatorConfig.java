package be.technifutur.gateway2.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    // /client/** -> client service
    // /movie/** -> movie service
//    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
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
                                .filters(req -> req.addRequestParameter("word", "gateway"))
                                .uri("lb://movie-service")
                )
                .build();
    }

}
