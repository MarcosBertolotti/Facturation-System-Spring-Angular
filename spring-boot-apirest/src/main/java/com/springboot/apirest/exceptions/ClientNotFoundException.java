package com.springboot.apirest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ClientNotFoundException extends HttpClientErrorException {


    public ClientNotFoundException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
