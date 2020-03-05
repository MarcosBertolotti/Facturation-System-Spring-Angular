package com.springboot.apirest.exceptions;

public class BillNotFoundException extends RuntimeException {

    public BillNotFoundException(){

        super("Bill not found in the System!");
    }
}
