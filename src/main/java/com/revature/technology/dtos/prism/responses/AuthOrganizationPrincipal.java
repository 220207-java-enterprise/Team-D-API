package com.revature.technology.dtos.prism.responses;

public class AuthOrganizationPrincipal {

    private String orgId;
    private String orgName;
    private String authCode;

    public AuthOrganizationPrincipal(){super();}

    public <T> AuthOrganizationPrincipal(String id, String subject, T authCode) {

    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "AuthOrganizationResponse{" +
                "orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
