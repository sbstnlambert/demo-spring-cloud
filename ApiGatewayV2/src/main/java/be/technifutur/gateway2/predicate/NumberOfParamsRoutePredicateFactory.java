package be.technifutur.gateway2.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Component
public class NumberOfParamsRoutePredicateFactory extends AbstractRoutePredicateFactory<NumberOfParamsRoutePredicateFactory.Config> {

    public NumberOfParamsRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (exchange) -> {
            int  numberOfParams = exchange.getRequest()
                    .getQueryParams()
                    .size();
            if (config.equals != null) return config.equals == numberOfParams;
            if (config.min != null && config.max != null) return config.min < numberOfParams && numberOfParams < config.max;
            if (config.min != null) return config.min < numberOfParams;
            if (config.max != null) return config.max < numberOfParams;
            return true;
        };
    }

    public static class Config {
        Integer equals;
        Integer min;
        Integer max;

        public Config() {
        }

        public Config(Integer equals) {
            this.equals = equals;
        }

        public Config(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }
    }
}
