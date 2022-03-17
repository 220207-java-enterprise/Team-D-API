package com.revature.technology.controllers;


import com.revature.technology.dtos.requests.ApproveOrDenyReimbursementRequest;
import com.revature.technology.dtos.requests.NewReimbursementRequest;
import com.revature.technology.dtos.requests.UpdatePendingReimbursementRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ReimbursementResponse;
import com.revature.technology.services.ReimbursementService;
import com.revature.technology.services.TokenService;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.util.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
    //An authenticated employee can view their pending reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/employee/all-pending-reimbursements
    @GetMapping(value = "employee/all-pending-reimbursements",produces = "application/json")
    public List<ReimbursementResponse> findAllPendingReimbursementsByEmployee(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        //EMPLOYEE can view their pending reimbursement requests
        List<ReimbursementResponse> allPendingReimbursements = reimbursementService.findAllPendingReimbursementsByEmployee(requester);

        return allPendingReimbursements;
    }

    //An authenticated employee can view and manage their pending reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/employee/reimbursement
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "employee/reimbursement", produces = "application/json", consumes = "application/json")
    public String updatePendingReimbursementByEmployee(@RequestBody UpdatePendingReimbursementRequest updatePendingReimbursementRequest, HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        return reimbursementService.updatePendingReimbursementByEmployee(updatePendingReimbursementRequest, requester);
    }


    //An authenticated employee can view their reimbursement request history (sortable and filterable)
    //url: http://localhost:8080/technology-project/reimbursements/employee/all-reimbursements
    @GetMapping(value = "employee/all-reimbursements",produces = "application/json")
    public List<ReimbursementResponse> findAllReimbursementsByEmployee(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        //EMPLOYEE can view their reimbursement request history
        List<ReimbursementResponse> allPendingReimbursements = reimbursementService.findAllReimbursementsByEmployee(requester);

        return allPendingReimbursements;
    }

    //An authenticated employee can submit a new reimbursement request
    //url: http://localhost:8080/technology-project/reimbursements/employee/reimbursement
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "employee/reimbursement", produces = "application/json", consumes = "application/json")
    public String submitNewReimbursementRequestByEmployee(@RequestBody NewReimbursementRequest newReimbursementRequest, HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        if(newReimbursementRequest.getAmount()<0 || newReimbursementRequest.getAmount()>=10000 ||
                newReimbursementRequest.getDescription() == null ||
                (!newReimbursementRequest.getReimbursementType().equals("LODGING") &&
                !newReimbursementRequest.getReimbursementType().equals("TRAVEL") &&
                !newReimbursementRequest.getReimbursementType().equals("FOOD") &&
                !newReimbursementRequest.getReimbursementType().equals("OTHER"))){
            //400 BAD REQUEST
            throw new InvalidRequestException("incorrect inputs");
        }


        return reimbursementService.submitNewReimbursementRequestByEmployee(newReimbursementRequest, requester);
    }


    //---------------------------------------------------------------------------------------------------------------
    //For Finance Manager
    //An authenticated finance manager can view a list of all pending reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/find-all-pending-reimbursements-by-finance-manager
    @GetMapping(value = "find-all-pending-reimbursements-by-finance-manager",produces = "application/json")
    public List<ReimbursementResponse> findAllPendingReimbursementsByFinanceManager(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));
        //TODO need to be implemented


        return null;
    }

    //An authenticated finance manager can view a history of requests that they have approved/denied
    //url: http://localhost:8080/technology-project/reimbursements/find-all-reimbursements-by-finance-manager
    @GetMapping(value = "find-all-reimbursements-by-finance-manager",produces = "application/json")
    public List<ReimbursementResponse> findAllReimbursementsByFinanceManager(HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        List<ReimbursementResponse> managersReimburementList =
                reimbursementService.findAllReimbursementsByManager(requester);

        return managersReimburementList;
    }

    //An authenticated finance manager can approve/deny reimbursement requests
    //url: http://localhost:8080/technology-project/reimbursements/approve-or-deny/<id>
    @PutMapping(value = "approve-or-deny/{reimbId}",produces = "application/json")
    public ReimbursementResponse approveOrDenyReimbursementByFinanceManager(@PathVariable String reimbId,
                                                           @RequestBody ApproveOrDenyReimbursementRequest approveOrDenyReimbursementRequest,
                                                            HttpServletRequest request){
        Principal requester = tokenService.extractRequesterDetails(request.getHeader("Authorization"));

        ReimbursementResponse resolvedReimbursement = reimbursementService.resolveReimbursement(reimbId, requester,
                approveOrDenyReimbursementRequest);

        return resolvedReimbursement;
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

