package com.example.route_alarm_app.client;

import com.example.route_alarm_app.dto.AccInfoResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PublicDataApiClient {

    private final WebClient webClient;
    private final String apiKey;

    public PublicDataApiClient(@Value("${seoul-api.key}") String apiKey, XmlMapper xmlMapper) {
        this.apiKey = apiKey;

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(xmlMapper, MediaType.APPLICATION_XML));
                    configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(xmlMapper, MediaType.APPLICATION_XML));
                }).build();

        this.webClient = WebClient.builder()
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .exchangeStrategies(strategies)
                .build();
    }

    public AccInfoResponse getAccidentInfo() {
        String uri = String.format("/%s/xml/AccInfo/1/100/", this.apiKey);

        return this.webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(AccInfoResponse.class)
                .block();
    }
}