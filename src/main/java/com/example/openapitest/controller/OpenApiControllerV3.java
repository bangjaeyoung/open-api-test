package com.example.openapitest.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 요청 데이터에 Header와 Body 정보를 담아서 요청하는 로직
 */
@RestController
@RequestMapping("/get-stock-info")
@CrossOrigin(originPatterns = "https://openapi.koreainvestment.com")
public class OpenApiControllerV3 {
    @GetMapping
    public ResponseEntity getStockInfo(
            @RequestHeader(value = "authorization") String authorization,
            @RequestHeader(value = "appkey") String appKey,
            @RequestBody String appSecret,
            @RequestBody String trId) {

        // 최종 반환 데이터 선언
        Map<String, Object> result = new HashMap<>();

        // 요청 header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", authorization);
        headers.set("appkey", appKey);

        // 요청 body 설정
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("appsecret", appSecret);
        requestBody.put("tr_id", trId);

        HttpEntity<Object> requestMessage = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        // http 요청 실시 / GET QueryString
        ResponseEntity<Object> response =
                restTemplate.exchange(
                        "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price",
                        HttpMethod.GET,
                        requestMessage,
                        Object.class
                );

        result.put("responseBody", response.getBody());
        HttpStatus httpStatus = response.getStatusCode();

        // 요청 성공 시, 문구 출력
        if (httpStatus.is2xxSuccessful()) System.out.println("Request Successfully!");

        return new ResponseEntity<>(result, httpStatus);
    }
}
