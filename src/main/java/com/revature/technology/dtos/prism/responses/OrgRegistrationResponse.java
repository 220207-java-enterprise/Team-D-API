package com.revature.technology.dtos.prism.responses;

public class OrgRegistrationResponse {

    private String orgId;
    private String authCode;


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
        return "OrgRegistrationResponse{" +
                "orgId='" + orgId + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
