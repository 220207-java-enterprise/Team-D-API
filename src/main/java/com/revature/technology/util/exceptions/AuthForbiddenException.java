package com.revature.technology.util.exceptions;

public class AuthForbiddenException extends RuntimeException{
    public AuthForbiddenException(){
        super("You don't have permission to do so.");
    }

    public AuthForbiddenException(String message) {
        super(message);
    }
}
