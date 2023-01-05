package com.example.openapitest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/get-stock-info")
@CrossOrigin(originPatterns = "https://openapi.koreainvestment.com")
public class OpenApiController {

    private final ObjectMapper objectMapper;

    public OpenApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity getStockInfo(
            @RequestParam("FID_COND_MRKT_DIV_CODE") String FID_COND_MRKT_DIV_CODE,
            @RequestParam("FID_INPUT_ISCD") String FID_INPUT_ISCD,
            @RequestHeader(value = "authorization") String authorization,
            @RequestHeader(value = "appkey") String appKey,
            @RequestHeader(value = "appsecret") String appSecret,
            @RequestHeader(value = "tr_id") String trId) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", authorization);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", trId);

//        String params = objectMapper.writeValueAsString(
//                RequestDto.builder()
//                        .FID_COND_MRKT_DIV_CODE(FID_COND_MRKT_DIV_CODE)
//                        .FID_INPUT_ISCD(FID_INPUT_ISCD)
//                        .build()
//        );

        Map<String, String> map = new LinkedHashMap<>();
        map.put("FID_COND_MRKT_DIV_CODE", FID_COND_MRKT_DIV_CODE);
        map.put("FID_INPUT_ISCD", FID_INPUT_ISCD);
        String params = objectMapper.writeValueAsString(map);

        HttpEntity<Object> requestMessage = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.exchange(
                        "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price",
                        HttpMethod.GET,
                        requestMessage,
                        Object.class
                );

        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }
}
