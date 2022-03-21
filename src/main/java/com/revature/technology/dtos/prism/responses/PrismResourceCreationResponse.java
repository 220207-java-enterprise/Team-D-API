package com.revature.technology.dtos.prism.responses;

public class PrismResourceCreationResponse {

    private String resourceId;

    public PrismResourceCreationResponse(){super();}

    public PrismResourceCreationResponse(String resourceId){
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "PrismResourceCreationResponse{" +
                "resourceId='" + resourceId + '\'' +
                '}';
    }
}
