package com.pixsecurity.pix_security.shared.exception;

public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
