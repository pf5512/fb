package com.footballer.rest.api.v1.response;

/**
 * Created by ian on 6/14/14.
 */

public class JsonResponse {

    public static final String SUCCESS = "success";
    public static final String NODATA = "noData";
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
