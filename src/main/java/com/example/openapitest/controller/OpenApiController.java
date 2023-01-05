package com.example.openapitest.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

@RestController
@RequestMapping("/get-stock-info")
@CrossOrigin(originPatterns = "https://openapi.koreainvestment.com")
public class OpenApiController {
    @GetMapping
    public ResponseEntity getStockInfo(
            @RequestParam("FID_COND_MRKT_DIV_CODE") String FID_COND_MRKT_DIV_CODE,
            @RequestParam("FID_INPUT_ISCD") String FID_INPUT_ISCD,
            @RequestHeader(value = "authorization") String authorization,
            @RequestHeader(value = "appkey") String appKey,
            @RequestHeader(value = "appsecret") String appSecret,
            @RequestHeader(value = "tr_id") String trId) {

        // 리턴 데이터 선언
        HashMap<String, Object> resultMap = new HashMap<>();

        // http 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", authorization);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", trId);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // http 요청 주소 및 쿼리 파라미터 설정
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", FID_COND_MRKT_DIV_CODE)
                .queryParam("FID_INPUT_ISCD", FID_INPUT_ISCD)
                .build(true);

        RestTemplate restTemplate = new RestTemplate();

        // http 요청 실시 / GET QueryString
        ResponseEntity<Object> response =
                restTemplate.exchange(
                        uriBuilder.toString(),
                        HttpMethod.GET,
                        entity,
                        Object.class
                );

        resultMap.put("responseBody", response.getBody());
        HttpStatus httpStatus = response.getStatusCode();

        // 요청 성공 시, 문구 출력
        if (httpStatus.is2xxSuccessful()) System.out.println("Request Successfully!");

        return new ResponseEntity<>(resultMap, httpStatus);
    }
}
