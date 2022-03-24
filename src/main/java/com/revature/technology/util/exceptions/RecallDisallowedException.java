package com.revature.technology.util.exceptions;

public class RecallDisallowedException extends ResourceNotFoundException{

    public RecallDisallowedException(){super("Cannot recall a Resolved Reimbursement");

    }
}
