package com.taxidispatcher.modules.user.application.port.out;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <a href="https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode">네이버 API 문서 링크</a>
 */
@Service
@Qualifier("naver")
public class NaverAddressInfoSearcher implements AddressInfoSearcher {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String API_BASE_URL = "https://naveropenapi.apigw.ntruss.com";
    private final String API_URI = "/map-geocode/v2/geocode";

    public NaverAddressInfoSearcher(
            @Value("${naver.client-id}") String CLIENT_ID,
            @Value("${naver.client-secret}") String CLIENT_SECRET) {
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    @Override
    public AddressInfo search(String address) {
        String res = RestClient.create(API_BASE_URL)
                .get()
                .uri(uriAddParams(address))
                .header("X-NCP-APIGW-API-KEY-ID", CLIENT_ID)
                .header("X-NCP-APIGW-API-KEY", CLIENT_SECRET)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> { throw new IllegalArgumentException("잘못된 요청"); })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> { throw new IllegalStateException("외부 서비스 호출 오류"); })
                .body(String.class);

        return null;
    }

    private String uriAddParams(String address) {
        return UriComponentsBuilder.fromPath(API_URI)
                .queryParam("query", address)
                .queryParam("page", 1)
                .queryParam("count", 1)
                .build()
                .toUriString();
    }
}
