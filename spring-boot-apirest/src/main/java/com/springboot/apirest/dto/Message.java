package com.springboot.apirest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String message;
}
