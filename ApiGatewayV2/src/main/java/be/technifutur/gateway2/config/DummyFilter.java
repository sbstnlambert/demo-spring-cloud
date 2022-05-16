package be.technifutur.gateway2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class DummyFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        List<String> auth = exchange.getRequest()
                .getHeaders()
                .get("Authorization");

        if( auth != null ){
            log.info("Auth: "+ (auth.get(0)));

            String[] userPass= new String(
                    Base64.getDecoder()
                            .decode(
                                    auth.get(0)
                                            .replace("Basic ", "")
                            ))
                    .split(":");

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userPass[0],
                            userPass[1],
                            List.of(new SimpleGrantedAuthority("USER"))
                    )
            );
        }
        return chain.filter(exchange);
    }
}
