package com.triforceblitz.triforceblitz.racetime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RacetimeConfig {
    @Bean
    public RacetimeService racetimeService() {
        var client = RestClient.builder().baseUrl("https://racetime.gg").build();
        var adapter = RestClientAdapter.create(client);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(RacetimeService.class);
    }
}
