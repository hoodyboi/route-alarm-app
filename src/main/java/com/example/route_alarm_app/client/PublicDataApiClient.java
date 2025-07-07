package com.example.route_alarm_app.client;

import com.example.route_alarm_app.dto.AccInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PublicDataApiClient {

    private final WebClient webClient;
    private final String apiKey;

    public PublicDataApiClient(@Value("${seoul-api.key}") String apiKey){
        this.webClient = WebClient.create("http://openapi.seoul.go.kr:8088");
        this.apiKey = apiKey;
    }

    public AccInfoResponse getAccidentInfo(){
        String uri = String.format("/%s/xml/AccInfo/1/100/", this.apiKey);

        return this.webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(AccInfoResponse.class)
                .block();
    }
}
