package com.revature.technology.controllers;
import com.revature.technology.dtos.NewUserRequest;
import com.revature.technology.dtos.ResourceCreationResponse;
import com.revature.technology.models.User;
import com.revature.technology.services.UserService;
import com.revature.technology.util.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController // TODO associates should look into the difference between @RestController and @Controller
@RequestMapping("/Users")
public class UserController {

    private UserService UserService;

    @Autowired
    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResourceCreationResponse registerUser(@RequestBody NewUserRequest request) throws IOException {
        return UserService.register(request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> handleInvalidRequests(InvalidRequestException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 400);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
    }

}