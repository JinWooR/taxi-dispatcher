package com.taxidispatcher.modules.user.application.port.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * <a href="https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord">카카오 API 문서 링크</a>
 */
@Service
@Qualifier("kakao")
@Primary
public class KakaoAddressInfoSearcherImpl implements AddressInfoSearcher {
    private final String API_KEY;
    private final String API_BASE_URL = "https://dapi.kakao.com";
    private final String API_URI = "/v2/local/search/address.json";

    public KakaoAddressInfoSearcherImpl(@Value("${kakao.rest-api}") String API_KEY) {
        this.API_KEY = API_KEY;
    }

    @Override
    public AddressInfo search(String address) {
        LocalAddressResponse res = RestClient.create(API_BASE_URL)
                .get()
                .uri(uriAddParams(address))
                .header("Authorization", "KakaoAK " + API_KEY)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> { throw new IllegalArgumentException("잘못된 요청"); })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> { throw new IllegalStateException("외부 서비스 호출 오류"); })
                .body(LocalAddressResponse.class);

        if (res.getDocuments().isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "카카오 주소 정보를 확인할 수 없습니다. : " + address);
        }

        var addr = Optional.ofNullable(res.getDocuments().get(0).getAddress())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "카카오 주소 상세 정보가 비었습니다: " + address));

        return new AddressInfo(addr.getRegion1depthName(), addr.getRegion2depthName(), addr.getRegion3depthName());
    }

    private String uriAddParams(String address) {
        return UriComponentsBuilder.fromPath(API_URI)
                .queryParam("query", address)
                .queryParam("page", 1)
                .queryParam("size", 1)
                .build()
                .toUriString();
    }

    @Getter
    public static class LocalAddressResponse {
        @JsonProperty
        private LocalAddressResponse.Meta meta;
        @JsonProperty
        private List<Document> documents;

        public static class Meta {
            @JsonProperty(value = "total_count")
            private Integer totalCount;
            @JsonProperty(value = "pageable_count")
            private Integer pageableCount;
            @JsonProperty(value = "is_end")
            private Boolean isEnd;
        }

        @Getter
        public static class Document {
            @JsonProperty(value = "address")
            private Address address; // 지번 주소 상세 정보
            @JsonProperty(value = "road_address")
            private RoadAddress roadAddress; // 도로명 주소 상세 정보
            @JsonProperty(value = "address_name")
            private String addressName; // 전체 지번 또는 도로명 주소
            @JsonProperty(value = "address_type")
            private String addressType; // address_name 값 타입중 하나
            @JsonProperty(value = "x")
            private String x; // X 좌표
            @JsonProperty(value = "y")
            private String y; // Y 좌푠

            @Getter
            public static class Address {
                @JsonProperty(value = "address_name")
                private String addressName; // 전체 지번 주소
                @JsonProperty(value = "region_1depth_name")
                private String region1depthName; // 시도명
                @JsonProperty(value = "region_2depth_name")
                private String region2depthName; // 시군구명
                @JsonProperty(value = "region_3depth_name")
                private String region3depthName; // 읍면동명
                @JsonProperty(value = "region_3depth_h_name")
                private String region3depthHName; // 행정동 명칭
                @JsonProperty(value = "h_code")
                private String hCode; // 행정동 코드
                @JsonProperty(value = "b_code")
                private String bCode; // 법정동 코드
                @JsonProperty(value = "mountain_yn")
                private String mountainYn; // 산 여부 (Y/N)
                @JsonProperty(value = "main_address_no")
                private String mainAddressNo; // 지번 주번지
                @JsonProperty(value = "sub_address_no")
                private String subAddressNo; // 지번 부번지, 없는 경우 빈 문자열("") 반환
                @JsonProperty(value = "x")
                private String x; // X 좌표
                @JsonProperty(value = "y")
                private String y; // Y 좌표
            }

            @Getter
            public static class RoadAddress {
                @JsonProperty(value = "address_name")
                private String addressName; // 전체 도로명 주소
                @JsonProperty(value = "region_1depth_name")
                private String region1depthName; // 시도명
                @JsonProperty(value = "region_2depth_name")
                private String region2depthName; // 시군구명
                @JsonProperty(value = "region_3depth_name")
                private String region3depthName; // 읍면동명
                @JsonProperty(value = "underground_yn")
                private String undergroundYn; // 지하 여부 (Y/N)
                @JsonProperty(value = "main_building_no")
                private String mainBuildingNo; // 건물 본번
                @JsonProperty(value = "sub_building_no")
                private String subBuildingNo; // 건물 부번, 없는 경우 빈 문자열("") 반환
                @JsonProperty(value = "building_name")
                private String buildingName; // 건물 이름
                @JsonProperty(value = "zone_no")
                private String zoneNo; // 우편번호 (5자리)
                @JsonProperty(value = "x")
                private String x; // X 좌표
                @JsonProperty(value = "y")
                private String y; // Y 좌표
            }
        }
    }
}
