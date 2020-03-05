package com.springboot.apirest.exceptions;

import static java.text.MessageFormat.format;

public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException(String object, String field, String value){

        super(format("{0} with {1} ''{2}'' already exists!", object, field, value));
    }
}
