package com.springboot.apirest.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    Integer code;
    String message;
}
