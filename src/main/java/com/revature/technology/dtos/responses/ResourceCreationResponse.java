package com.revature.technology.dtos.responses;

public class ResourceCreationResponse {

    private String id;

    // Jackson still needs a no-args constructor
    public ResourceCreationResponse() {
        super();
    }

    public ResourceCreationResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResourceCreationResponse{" +
                "id='" + id + '\'' +
                '}';
    }

}
