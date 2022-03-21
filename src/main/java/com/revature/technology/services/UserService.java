package com.revature.technology.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.technology.dtos.prism.responses.AuthOrganizationPrincipal;
import com.revature.technology.dtos.prism.responses.OrgRegistrationResponse;
import com.revature.technology.dtos.requests.LoginRequest;
import com.revature.technology.dtos.requests.NewUserRequest;
import com.revature.technology.dtos.requests.UserUpdateRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ResourceCreationResponse;
import com.revature.technology.dtos.responses.UserResponse;
import com.revature.technology.models.User;
import com.revature.technology.models.UserRole;
import com.revature.technology.repositories.UserRepository;
import com.revature.technology.util.DummyDataInserter;
import com.revature.technology.util.PrismClient;
import com.revature.technology.util.exceptions.AuthenticationException;
import com.revature.technology.repositories.UserRoleRepository;
import com.revature.technology.util.exceptions.ForbiddenException;
import com.revature.technology.util.exceptions.InvalidRequestException;
import com.revature.technology.util.exceptions.ResourceConflictException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private static PrismClient prismClient;
    private static DummyDataInserter prismSetup;

    @Autowired
    public UserService(UserRepository UserRepo, UserRoleRepository userRoleRepository, PrismClient prismClient,
                       DummyDataInserter prismSetup) {
        this.userRepository = UserRepo;
        this.userRoleRepository = userRoleRepository;
        this.prismClient = prismClient;
        this.prismSetup = prismSetup;
    }

    // ***********************************
    //      ADMIN ONLY GET ALL USERS
    // ***********************************
    public List<UserResponse> getAllEmployees(){
        List<User> users = (List<User>) userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users){
            userResponses.add(new UserResponse(user));
        }
        return userResponses;
    }

    // ***********************************
    //      Register USER/Manager (ADMIN NOT ALLOWED)
    // ***********************************
    public User register(NewUserRequest newUserRequest) throws JsonProcessingException {
        User newUser = newUserRequest.extractUser();

        //This no longer worked after changing ADMIN role Id to 1 and so on.
        //UserRole myRole = userRoleRepository.getUserRoleByRole(newUserRequest.getRole());
        //newUser.setRole(myRole);

        //breadcrumb statements
        //System.out.println(newUser);
        //System.out.println(newUserRequest);

        if (!isUserValid(newUser) || newUserRequest.getRole().equals("ADMIN")) {
            throw new InvalidRequestException("Bad registration details were given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "username ";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }

        //breadcrumb
        //System.out.println(myRole);

        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setIsActive(false);
        newUser.setPassword(BCrypt.hashpw(newUserRequest.getPassword(), BCrypt.gensalt(10)));

        // only Employees will be stored to Prism
        if (newUser.getRole().getRole().equals("EMPLOYEE")) {
            String payeeId = prismClient.registerNewEmployeeUsingPrism(prismSetup.getAuthOrg(), newUser);
            newUser.setPayeeId(payeeId);
        }
        userRepository.save(newUser);

        return newUser;
    }

    // ***********************************
    //      ADMIN UPDATE USER
    // ***********************************
    public void updateUser(UserUpdateRequest userUpdate){
        User newUser = userRepository.getUserById(userUpdate.getUser_id());
        if (newUser.getRole().getRole().equals("ADMIN"))
            throw new InvalidRequestException("Cannot remove admin");

        UserRole myRole = userRoleRepository.getUserRoleByRole(userUpdate.getRole());

        //Check for any updates then prepare User to be updated
        if(userUpdate.getGiven_name() != null)
            newUser.setGivenName(userUpdate.getGiven_name());
        if(userUpdate.getSurname() != null)
            newUser.setGivenName(userUpdate.getGiven_name());
        if(userUpdate.getEmail() != null)
            newUser.setEmail(userUpdate.getEmail());
        if(userUpdate.getUsername() != null)
            newUser.setUsername(userUpdate.getUsername());
        if(userUpdate.getPassword() != null) {
            newUser.setPassword(userUpdate.getPassword());
            if (!isUserValid(newUser)){
                throw new InvalidRequestException("Bad update details were given.");
            }
            newUser.setPassword(BCrypt.hashpw(userUpdate.getPassword(), BCrypt.gensalt(10)));
        }
        if(userUpdate.isActive() != null )
            newUser.setIsActive(userUpdate.isActive());
        if(userUpdate.getRole() != null)
            newUser.setRole(myRole);

        if (newUser.getRole().getRole().equals("ADMIN"))
            throw new InvalidRequestException("Cannot promote to admin");

        userRepository.save(newUser);
    }

    // ***********************************
    //      ADMIN "DELETE" USER
    // ***********************************
    public void deleteUser(UserUpdateRequest userUpdate){
        User newUser = userRepository.getUserById(userUpdate.getUser_id());
        if (newUser.getRole().getRole().equals("ADMIN"))
            throw new InvalidRequestException("Cannot delete admin");

        newUser.setIsActive(false);

        userRepository.save(newUser);
    }

    // ***********************************
    //      LOGIN USER
    // ***********************************

    public User login(LoginRequest loginRequest){

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!isUsernameValid(username) || !isPasswordValid(password)){
            throw new InvalidRequestException("Invalid credentials provided");
        }

        Optional<User> authUser = userRepository.getUserByUsername(username);
        if(!BCrypt.checkpw(password, authUser.get().getPassword())) {
            throw new AuthenticationException();
        }
        // Check for if user exists then check if user is active
        if (!authUser.isPresent()) {
            throw new AuthenticationException();
        }
        if (!authUser.get().getIsActive()) {
            throw new ForbiddenException();
        }

        return authUser.get();
    }

    public boolean isUserActive(String id){

        User user = userRepository.getUserById(id);
        return user.getIsActive();
    }

    protected boolean isUserValid(User appUser) {

        // First name and last name are not just empty strings or filled with whitespace
        if (appUser.getGivenName().trim().equals("") || appUser.getSurname().trim().equals("")) {
            return false;
        }
        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(appUser.getUsername())) {
            return false;
        }
        // Passwords require a minimum eight characters, at least one uppercase letter, one lowercase
        // letter, one number and one special character
        if (!isPasswordValid(appUser.getPassword())) {
            return false;
        }
        if(!isRoleValid(appUser.getRole())) {
            return false;
        }
        if(!isEmailValid(appUser.getEmail())){
            return false;
        }
        return true;

    }

    public boolean isRoleValid(UserRole role){
        ArrayList<String> validRoles = new ArrayList<String>();
        validRoles.add("FINANCE MANAGER");
        validRoles.add("ADMIN");
        validRoles.add("EMPLOYEE");

        if (!validRoles.contains(role.getRole())){
            return false;
        }
        return true;
    }

    public boolean isEmailValid(String email) {
        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean isUsernameAvailable(String username) {
       return !userRepository.getUserByUsername(username).isPresent();
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.getUserByEmail(email) == null;
    }

}
