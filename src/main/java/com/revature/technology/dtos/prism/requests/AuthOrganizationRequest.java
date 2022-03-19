package com.revature.technology.dtos.prism.requests;

import com.revature.technology.dtos.prism.responses.OrgRegistrationResponse;

public class AuthOrganizationRequest {

    private String orgId;
    private String authCode;

    private OrgRegistrationResponse orgRegistrationResponse;

    public AuthOrganizationRequest(){super();}

    public AuthOrganizationRequest(OrgRegistrationResponse orgRegistrationResponse){
        this.orgId = orgRegistrationResponse.getOrgId();
        this.authCode = orgRegistrationResponse.getAuthCode();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "AuthOrganizationRequest{" +
                "orgId='" + orgId + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
