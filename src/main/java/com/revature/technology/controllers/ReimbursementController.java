package com.revature.technology.controllers;


import com.revature.technology.dtos.requests.ApproveOrDenyReimbursementRequest;
import com.revature.technology.dtos.requests.LoginRequest;
import com.revature.technology.dtos.requests.NewReimbursementRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ReimbursementResponse;
import com.revature.technology.models.Reimbursement;
import com.revature.technology.services.ReimbursementService;
import com.revature.technology.services.TokenService;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.util.exceptions.ForbiddenException;
import com.revature.technology.util.exceptions.InvalidRequestException;
import com.revature.technology.util.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {
    private final ReimbursementService reimbursementService;
    private final TokenService tokenService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, TokenService tokenService){
        this.reimbursementService = reimbursementService;
        this.tokenService = tokenService;
    }

    //---------------------------------------------------------------------------------------------------------------
    //For Employee
    //An authenticated employee can view and manage their pending reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/find-all-pending-reimbursements-by-employee
    @GetMapping(value = "find-all-pending-reimbursements-by-employee",produces = "application/json")
    public List<ReimbursementResponse> findAllPendingReimbursementsByEmployee(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));
        //TODO need to be implemented


        return null;
    }

    //An authenticated employee can view their reimbursement request history (sortable and filterable)
    //url: http://localhost:8080/technology-project/reimbursements/find-all-reimbursements
    @GetMapping(value = "find-all-reimbursements",produces = "application/json")
    public List<ReimbursementResponse> findAllReimbursementsByEmployee(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        List<ReimbursementResponse> allPendingReimbursements = reimbursementService.findAllReimbursementsByEmployee(requester);
        return allPendingReimbursements;
    }

    //An authenticated employee can submit a new reimbursement request
    //url: http://localhost:8080/technology-project/reimbursements/submit-a-reimbursement
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "submit-a-reimbursement", produces = "application/json", consumes = "application/json")
    public void submitNewReimbursementRequestByEmployee(@RequestBody NewReimbursementRequest newReimbursementRequest){
        //TODO need to be implemented

    }


    //---------------------------------------------------------------------------------------------------------------
    //For Finance Manager
    //An authenticated finance manager can view a list of all pending reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/find-all-pending-reimbursements-by-finance-manager
    @GetMapping(value = "find-all-pending-reimbursements-by-finance-manager",produces = "application/json")
    public List<ReimbursementResponse> findAllPendingReimbursementsByFinanceManager(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        List<ReimbursementResponse> pendingReimbursementList = reimbursementService.findAllPendingReimbursement(requester);

        return pendingReimbursementList;
    }

    //An authenticated finance manager can view a history of requests that they have approved/denied
    //url: http://localhost:8080/technology-project/reimbursements/find-all-reimbursements-by-finance-manager
    @GetMapping(value = "find-all-reimbursements-by-finance-manager",produces = "application/json")
    public List<ReimbursementResponse> findAllReimbursementsByFinanceManager(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));
        //TODO need to be implemented
        List<ReimbursementResponse> managersReimburementList =
                reimbursementService.findAllReimbursementsByManager(requester);

        return managersReimburementList;
    }

    //An authenticated finance manager can approve/deny reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/approve-or-deny
    @PutMapping(value = "approve-or-deny",produces = "application/json")
    public void handleReimbursementRequestByFinanceManager(@RequestBody ApproveOrDenyReimbursementRequest approveOrDenyReimbursementRequest,
                                                            HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));
        //TODO need to be implemented


    }


    //---------------------------------------------------------------------------------------------------------------
    //For Exception Handler
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> handleInvalidRequest(InvalidRequestException e){
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 400);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());

        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> handleForbiddenRequest(ForbiddenException e){
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 400);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());

        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HashMap<String, Object> handleUserNotLoggedIn(NotLoggedInException e){
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 400);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());

        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HashMap<String, Object> handleAuthenticationException(AuthenticationException e){
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());

        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, Object> handleAnyOtherException(Exception e){
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 500);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());

        return responseBody;
    }
}

