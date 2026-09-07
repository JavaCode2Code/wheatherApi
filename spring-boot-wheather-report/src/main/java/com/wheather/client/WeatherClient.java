package com.wheather.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WeatherClient {
    private WebClient webClient;
    private String apiKey;

    public WeatherClient(@Value("${weather.api.base-url}") String baseUrl,
                         @Value("${weather.api.key}") String apiKey) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Mono<Map> getCurrentWeather(String city) {
        return webClient.get()
            .uri(uri -> uri.path("/weather")
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric").build())
            .retrieve()
            .bodyToMono(Map.class);
    }
}
