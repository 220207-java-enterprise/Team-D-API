package com.revature.technology.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.technology.dtos.requests.NewUserRequest;
import com.revature.technology.dtos.requests.UserUpdateRequest;
import com.revature.technology.dtos.responses.ResourceCreationResponse;
import com.revature.technology.dtos.responses.UserResponse;
import com.revature.technology.models.User;
import com.revature.technology.services.UserService;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.util.exceptions.ForbiddenException;
import com.revature.technology.util.exceptions.InvalidRequestException;
import com.revature.technology.util.exceptions.ResourceConflictException;
import com.revature.technology.util.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Admin get all users
    @CrossOrigin
    @Secured(allowedRoles = "ADMIN")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public HashMap<String, Object> getAllUsers() {
        HashMap<String, Object> userList = new HashMap<String, Object>();
        List<UserResponse> myUsers = userService.getAllEmployees();
        userList.put("endpoint", " /user");
        userList.put("status", "UP");
        userList.put("providedValues", myUsers);
        return userList;
    }


    @CrossOrigin
    @Secured(allowedRoles = "ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(produces = "application/json", consumes = "application/json")
    public void update(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    // Admin "Delete" user
    @Secured(allowedRoles = "ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = "application/json", consumes = "application/json")
    public void delete(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.deleteUser(userUpdateRequest);
    }


    // Register as User/Manager
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public HashMap<String, Object> register(@RequestBody NewUserRequest newUserRequest) throws JsonProcessingException {
        HashMap<String, Object> userList = new HashMap<String, Object>();

        User newUser = userService.register(newUserRequest);
        UserResponse userResponse = new UserResponse(newUser);

        userList.put("endpoint", " /register");
        userList.put("status", "UP");
        userList.put("providedValues", userResponse);
        System.out.println(userList);
        System.out.println(newUser);

        return userList;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HashMap<String, Object> handleAuthenticationException(AuthenticationException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
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
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HashMap<String, Object> handleForbiddenException(ForbiddenException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 403);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public HashMap<String, Object> handleResourceConflictExceptions(ResourceConflictException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 409);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
    }

}