package com.revature.technology.util.exceptions;

public class NotLoggedInException extends ResourceNotFoundException{

    public NotLoggedInException(){super("Please login before making requests.");

    }
}