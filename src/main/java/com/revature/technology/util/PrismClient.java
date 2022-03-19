package com.revature.technology.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.technology.dtos.prism.requests.AddEmployeeToOrgRequest;
import com.revature.technology.dtos.prism.requests.AuthOrganizationRequest;
import com.revature.technology.dtos.prism.requests.NewOrgRequest;
import com.revature.technology.dtos.prism.requests.PostPaymentRequest;
import com.revature.technology.dtos.prism.responses.AuthOrganizationPrincipal;
import com.revature.technology.dtos.prism.responses.OrgRegistrationResponse;
import com.revature.technology.dtos.prism.responses.PrismResourceCreationResponse;
import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

// TODO make this a bean and use IoC container to auto-wire dependencies
@Component
public class PrismClient {

    public static RestTemplate prismClient = new RestTemplate();

    public PrismClient() {
    }

    public OrgRegistrationResponse registerNewOrganizationUsingPrism() throws JsonProcessingException {
        // Set content type for the request to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // prepare the request payload using NewOrgRequest
        NewOrgRequest newOrgRequest = new NewOrgRequest("Team-D-Organization", "some-secret-key");
        HttpEntity<NewOrgRequest> request = new HttpEntity<>(newOrgRequest, headers);

        // make the request by attaching a payload and parsing a response
        OrgRegistrationResponse response = prismClient.postForObject("http://localhost:5000/prism/organizations",
                request, OrgRegistrationResponse.class);

        System.out.println("Org Registered--> "+response);
        return response;
    }

    public ResponseEntity<AuthOrganizationPrincipal> authenticateOrganizationUsingPrism(OrgRegistrationResponse orgRegistrationResponse){

        // Set content type for the request to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AuthOrganizationRequest authRequest = new AuthOrganizationRequest(orgRegistrationResponse);

        HttpEntity<AuthOrganizationRequest> authOrgRequest = new HttpEntity<>(authRequest, headers);

        // make the request by attaching a payload and parsing a response
        ResponseEntity<AuthOrganizationPrincipal> authOrgResponse = prismClient.postForEntity(
                "http://localhost:5000/prism/auth", authOrgRequest, AuthOrganizationPrincipal.class);

        System.out.println("Org Auth Complete --> "+authOrgResponse);
        return authOrgResponse;
    }

    public String registerNewEmployeeUsingPrism(ResponseEntity<AuthOrganizationPrincipal> authOrganizationPrincipal,
                                              User newUser) {

        HttpHeaders headers = authOrganizationPrincipal.getHeaders();
        System.out.println("Org token--> "+headers);

        AddEmployeeToOrgRequest addEmployeeRequestObject = new AddEmployeeToOrgRequest(newUser.getGivenName(), newUser.getSurname(),
                newUser.getEmail(), new AddEmployeeToOrgRequest.AccountInfo(
                "Test Bank", "111222333", "123456789"));

        // create a request to prism, inject newUser into the request (with Dummy AccountInfo data
        HttpEntity<AddEmployeeToOrgRequest> addEmployeeToOrgRequestHttpEntity = new HttpEntity<>(addEmployeeRequestObject,
                headers);

        ResponseEntity<PrismResourceCreationResponse> addEmployeeResponse = prismClient.postForEntity(
                "http://localhost:5000/prism/employees", addEmployeeToOrgRequestHttpEntity,
                PrismResourceCreationResponse.class);

        String payeeId = addEmployeeResponse.getBody().getResourceId();
        System.out.println("PayeeId---->"+payeeId);
        return payeeId;
    }

    public String postPaymentUsingPrism(ResponseEntity<AuthOrganizationPrincipal> authOrganizationPrincipal,
                                      User employee, Reimbursement reimbursement){

        HttpHeaders headers = authOrganizationPrincipal.getHeaders();
        System.out.println("Org token--> "+headers);

        PostPaymentRequest postPaymentRequest = new PostPaymentRequest(employee.getPayeeId(),
                reimbursement.getAmount());

        HttpEntity<PostPaymentRequest> postPaymentRequestHttpEntity = new HttpEntity<>(postPaymentRequest, headers);

        ResponseEntity<PrismResourceCreationResponse> postPaymentResponse = prismClient.postForEntity(
                "http://localhost:5000/prism/payments", postPaymentRequestHttpEntity,
                PrismResourceCreationResponse.class);

        String paymentId = postPaymentResponse.getBody().getResourceId();

        return paymentId;
    }
}
