package com.springboot.apirest.exceptions;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends RuntimeException {


    public ClientNotFoundException() {
        super("Client not found in the System!");
    }
}
