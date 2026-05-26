package com.tecsup.petclinic.exceptions;

public class VetSpecialtyAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public VetSpecialtyAlreadyExistsException(String message) {
        super(message);
    }
}
