package com.example.openapitest.controller;

import com.example.openapitest.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/get-stock-info")
@CrossOrigin(originPatterns = "https://openapi.koreainvestment.com:9443")
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
        System.out.println("headers = " + headers);
        System.out.println();

//        String params = objectMapper.writeValueAsString(
//                RequestDto.builder()
//                        .FID_COND_MRKT_DIV_CODE(FID_COND_MRKT_DIV_CODE)
//                        .FID_INPUT_ISCD(FID_INPUT_ISCD)
//                        .build()
//        );
//        System.out.println("params = " + params);
//        System.out.println();

        Map<String, String> map = new LinkedHashMap<>();
        map.put("FID_COND_MRKT_DIV_CODE", FID_COND_MRKT_DIV_CODE);
        map.put("FID_INPUT_ISCD", FID_INPUT_ISCD);
        String params = objectMapper.writeValueAsString(map);
        System.out.println("params = " + params);
        System.out.println();

        HttpEntity entity = new HttpEntity(params, headers);
        System.out.println("entity = " + entity);
        System.out.println();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.exchange(
                        "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price",
                        HttpMethod.GET,
                        entity,
                        Object.class
                );

        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }

//    public ResponseEntity call(String FID_COND_MRKT_DIV_CODE, String FID_INPUT_ISCD, String authorization, String appkey, String appSecret, String trId) {
//
//        // 국내 주식 시세 조회
//        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";
//        String tr_id = trId;
//        String data = "{\n" +
//                "    \"fid_cond_mrkt_div_code\": \"FID조건시장분류코드\",\n" +
//                "    \"fid_input_iscd\": \"FID입력종목코드\"\n" +
//                "}";
//
//        httpPostBodyConnection(url,data,tr_id);
//    }
//
//    public static void httpPostBodyConnection(String UrlData, String ParamData, String TrId) throws IOException {
//        String totalUrl = "";
//        totalUrl = UrlData.trim().toString();
//
//        URL url = null;
//        HttpURLConnection conn = null;
//
//        String responseData = "";
//        BufferedReader br = null;
//
//        StringBuffer sb = new StringBuffer();
//        String returnData = "";
//
//        try{
//            url = new URL(totalUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("authorization", "Bearer {TOKEN}");
//            conn.setRequestProperty("appKey", "{Client_ID}");
//            conn.setRequestProperty("appSecret", "{Client_Secret}");
//            conn.setRequestProperty("personalSeckey", "{personalSeckey}");
//            conn.setRequestProperty("tr_id", TrId);
//            conn.setRequestProperty("tr_cont", " ");
//            conn.setRequestProperty("custtype", "법인(B), 개인(P)");
//            conn.setRequestProperty("seq_no", "법인(01), 개인( )");
//            conn.setRequestProperty("mac_address", "{Mac_address}");
//            conn.setRequestProperty("phone_num", "P01011112222");
//            conn.setRequestProperty("ip_addr", "{IP_addr}");
//            conn.setRequestProperty("hashkey", "{Hash값}");
//            conn.setRequestProperty("gt_uid", "{Global UID}");
//            conn.setDoOutput(true);
//
//            try (OutputStream os = conn.getOutputStream()) {
//                byte request_data[] = ParamData.getBytes("utf-8");
//                os.write(request_data);
//                os.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            conn.connect();
//            System.out.println("http 요청 방식" + "POST BODY JSON");
//            System.out.println("http 요청 타입" + "application/json");
//            System.out.println("http 요청 주소" + UrlData);
//            System.out.println("");
//
//            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        } catch (IOException e){
//            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
//        } finally {
//            try {
//                sb = new StringBuffer();
//                while ((responseData = br.readLine()) != null) {
//                    sb.append(responseData);
//                }
//                returnData = sb.toString();
//                String responseCode = String.valueOf(conn.getResponseCode());
//                System.out.println("http 응답 코드 : " + responseCode);
//                System.out.println("http 응답 데이터 : " + returnData);
//                if (br != null){
//                    br.close();
//                }
//            } catch (IOException e){
//                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
//            }
//        }
//    }
}
