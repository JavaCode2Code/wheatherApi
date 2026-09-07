package com.wheather.service;

import com.wheather.client.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {
    @Autowired
    private WeatherClient client;


    @Cacheable(value = "weather", key = "#city")
    public Mono<Object> getWeather(String city) {
        return client.getCurrentWeather(city)
                .map(response -> {
                    if (response.containsKey("cod") && response.get("cod").equals(200)) {
                        return response;
                    } else {
                        return null;
                    }
                });
    }
}
