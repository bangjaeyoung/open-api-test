package com.example.openapitest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestDto {
    private String FID_COND_MRKT_DIV_CODE;
    private String FID_INPUT_ISCD;
}
