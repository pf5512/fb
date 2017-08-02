package com.footballer.rest.api.v2.Response;

public class JsonResponse {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String status;
    private String message;

    public JsonResponse() {
    	
    }

    public JsonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
