package be.technifutur.gateway2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class DummyFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.create(sink -> {
            List<String> auth = exchange.getRequest().getHeaders()
                    .get("Authorization");
            log.info("Auth: " + (auth == null ? "No auth": auth.get(0)));
            chain.filter(exchange);
        });
    }
}
