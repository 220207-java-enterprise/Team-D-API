package com.revature.technology.controllers;

import com.fasterxml.jackson.databind.DatabindException;
import com.revature.technology.dtos.requests.LoginRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.services.TokenService;
import com.revature.technology.services.UserService;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.util.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public AuthController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @CrossOrigin(exposedHeaders = "authorization")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public Principal login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Principal principal = new Principal(userService.login(request));

        //todo implement with front end
//        if (!userService.isUserActive(principal.getId())) {
//            response.setStatus(403);    // user is not active
//            return null;
//        }

        String token = tokenService.generateToken(principal);
        response.setHeader("Authorization", token);
        response.setStatus(201);

        System.out.println(principal);
        return principal;
    }

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
