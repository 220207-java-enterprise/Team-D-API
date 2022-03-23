package com.revature.technology.services;

import com.revature.technology.dtos.requests.LoginRequest;
import com.revature.technology.dtos.requests.NewUserRequest;
import com.revature.technology.dtos.responses.ResourceCreationResponse;
import com.revature.technology.models.User;
import com.revature.technology.models.UserRole;
import com.revature.technology.repositories.UserRepository;

import com.revature.technology.repositories.UserRoleRepository;
import com.revature.technology.util.DummyDataInserter;
import com.revature.technology.util.PrismClient;
import com.revature.technology.util.exceptions.InvalidRequestException;
import com.revature.technology.util.exceptions.ResourceConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    private UserService sut;
    private UserRepository mockUserRepository;
    private UserRoleRepository mockUserRoleRepository;
    private PrismClient mockPrismClient;
    private DummyDataInserter mockPrismSetup;

    @BeforeEach
    public void setup(){
        mockUserRoleRepository = mock(UserRoleRepository.class);
        mockUserRepository = mock(UserRepository.class);
        mockPrismClient = mock(PrismClient.class);
        mockPrismSetup = mock(DummyDataInserter.class);

        sut = new UserService(mockUserRepository, mockUserRoleRepository, mockPrismClient, mockPrismSetup);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenEmptyString(){
        String username ="";
        // Act
        boolean result = sut.isUsernameValid(username);
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenNullString(){
        // Arrange
        String username = null;
        // Act
        boolean result = sut.isUsernameValid(username);
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenShortUsername(){
        //Arrange
        String username="short";
        //Act
        boolean result = sut.isUsernameValid(username);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenLongUsername(){
        //Arrange
        String username="waytoolongofausernameforourapplication";
        //Act
        boolean result = sut.isUsernameValid(username);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenUsernameWithIllegalCharacters(){
        //Arrange
        String username="tester99!";
        //Act
        boolean result = sut.isUsernameValid(username);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsTrue_givenValidUsername(){
        //Arrange
        String username="4bhilekh";
        //Act
        boolean result = sut.isUsernameValid(username);
        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void test_isRoleValid_returnsTrue_givenValidRole(){
        // Arrange
        UserRole validUserRole = new UserRole("1", "ADMIN");
        // Act
        boolean result = sut.isRoleValid(validUserRole);
        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void test_isEmailValid_returnsFalse_givenInvalidEmail(){
        // Arrange
        String email = "emailemailcom";
        // Act
        boolean result = sut.isEmailValid(email);
        System.out.println(result+" "+email);
        //Assert
        Assertions.assertFalse(result);
    }


    @Test
    public void test_isUserValid_givenInvalidUserUsername(){
        //Arrange
        User invalidUser = new User("1","user", "email@email.com", "p4$$WORD", "John", "Doe", true, new UserRole());

        //Act
        boolean result = sut.isUserValid(invalidUser);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUserValid_givenInvalidUserEmail(){
        //Arrange
        User invalidUser = new User("1","username", "emailemailcom", "p4$$WORD", "John", "Doe", true, new UserRole());

        //Act
        boolean result = sut.isUserValid(invalidUser);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUserValid_givenInvalidUserPassword(){
        //Arrange
        User invalidUser = new User("1","username", "email@email.com", "password", "John", "Doe", true,
                new UserRole());

        //Act
        boolean result = sut.isUserValid(invalidUser);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUserValid_givenInvalidUserGivenName(){
        //Arrange
        User invalidUser = new User("1","username", "email@email.com", "p4$$WORD", "    ", "Doe", true, new UserRole());

        //Act
        boolean result = sut.isUserValid(invalidUser);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUserValid_givenInvalidUserSurname(){
        //Arrange
        User invalidUser = new User("1","username", "email@email.com", "p4$$WORD", "John", "", true, new UserRole());

        //Act
        boolean result = sut.isUserValid(invalidUser);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void test_isUserValid_givenValidUser(){

        //Arrange
        UserRole userRole = new UserRole("1","ADMIN");
        User validUser = new User(UUID.randomUUID().toString(),"username", "email@email.com", "p4$$WORD", "john",
                "doe",
                true, userRole);

        //Act
        boolean result = sut.isUserValid(validUser);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void test_isUsernameAvailable_givenDuplicateUsername(){
        // Arrange
        String username = "4bhilekh";
        when(mockUserRepository.getUserByUsername(username)).thenReturn(new User());
        // Act
        boolean result = sut.isUsernameAvailable(username);

        Assertions.assertFalse(result);
    }

    @Test
    public void test_isEmailAvailable_givenDuplicateEmail(){
        // Arrange
        String email = "duplicate@email.com";
        when(mockUserRepository.getUserByEmail(email)).thenReturn(new User());
        // Act
        boolean result = sut.isEmailAvailable(email);

        Assertions.assertFalse(result);
    }

    @Test
    public void test_registration_givenValidUser() throws IOException {
        //Arrange
        NewUserRequest newUserRequest = new NewUserRequest("username", "email@email.com", "p4$$word", "john", "doe",
                "EMPLOYEE");

        User validUser = newUserRequest.extractUser();

        UserService spiedSut = Mockito.spy(sut);
        when(spiedSut.isUsernameValid(validUser.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(validUser.getPassword())).thenReturn(true);
        when(spiedSut.isEmailValid(validUser.getEmail())).thenReturn(true);
        when(spiedSut.isUserValid(validUser)).thenReturn(true);

        doReturn(new User()).when(mockUserRepository).save(validUser);

        User registerResult = spiedSut.register(newUserRequest);

        Assertions.assertNotNull(registerResult);
    }


    @Test
    public void test_registration_throwsResourceConflictException_givenDuplicateUsernameAndEmail() throws ResourceConflictException, IOException {

        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest duplicateUserRequest = new NewUserRequest("4bhilekh", "abhilekh390@revature.net", "p4$$WORD",
                "Abhilekh", "Adhikari", "EMPLOYEE");

        User duplicateUserToSave = duplicateUserRequest.extractUser();

        String username = duplicateUserToSave.getUsername();
        String email = duplicateUserToSave.getEmail();

        when(mockUserRepository.getUserByUsername(username)).thenReturn(new User());

        when(mockUserRepository.getUserByEmail(email)).thenReturn(new User());

        try {
            Exception exception = assertThrows(ResourceConflictException.class, () -> {
                spiedSut.register(duplicateUserRequest);
            });

            String exceptionMessage = exception.getMessage();
            Assertions.assertNotNull(exceptionMessage);
        } finally {
            verify(mockUserRepository, times(0)).save(duplicateUserToSave);
        }
    }

    @Test
    public void test_registration_throwsRuntimeException_givenInvalidUser() throws IOException {

        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest invalidUserRequest = new NewUserRequest("usrnm", "email@email", "password", "joe", "rogan",
                "INVALID ROLE");

        User invalidUserToSave = invalidUserRequest.extractUser();

        try {
            Exception exception = assertThrows(InvalidRequestException.class, () -> {
                spiedSut.isUserValid(invalidUserToSave);
                spiedSut.register(invalidUserRequest);
            });

            String exceptionMessage = exception.getMessage();
            Assertions.assertNotNull(exceptionMessage);
        } finally {
            verify(spiedSut, times(1)).isUserValid(invalidUserToSave);
            verify(spiedSut, times(0)).isUsernameAvailable(invalidUserToSave.getUsername());
            verify(spiedSut, times(0)).isEmailAvailable(invalidUserToSave.getEmail());
            verify(mockUserRepository, times(0)).save(invalidUserToSave);
        }
    }


    @Test
    public void test_login_returnsAuthenticatedAppUser_givenValidAndKnownCredentials(){

        //Arrange
        LoginRequest loginRequest = new LoginRequest("4bhilekh", "p4$$word");
        UserService spiedSut = Mockito.spy(sut);
        String username =  loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Act
        when(spiedSut.isUsernameValid(username)).thenReturn(true);
        when(spiedSut.isPasswordValid(password)).thenReturn(true);
        // How can I return a potentialUser (containing the hashed password?)
        when(mockUserRepository.getUserByUsername(username)).thenReturn(new User());

        if(loginRequest.getPassword().equals(loginRequest.getPassword())) {
            User authUser = mockUserRepository.getUserByUsername(username);
            // Assert
            Assertions.assertNotNull(authUser);
        }
    }

    @Test
    public void test_login_throwsRuntimeException_givenUnknownUserCredentials() {
        //Arrange
        String unknownUsername = "unknownuser";
        String somePassword = "p4$$WORD";
        LoginRequest loginRequest = new LoginRequest(unknownUsername, somePassword);
        when(mockUserRepository.getUserByUsernameAndPassword(unknownUsername, somePassword)).thenReturn(null);

        //Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.login(loginRequest);
        });

        String exceptionMessage = exception.getMessage();
        Assertions.assertNotNull(exceptionMessage);
    }

    @Test
    public void test_login_throwsInvalidRequestException_givenInvalidUsername() {
        // Arrange
        String invalidUsername = "no";
        String validPassword = "p4$$word";
        LoginRequest loginRequest = new LoginRequest(invalidUsername, validPassword);

        //Act
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            sut.login(loginRequest);
        });

        String exceptionMessage = exception.getMessage();
        Assertions.assertNotNull(exceptionMessage);
    }


}
