package net.restuarant.service.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicationError {

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    @Expose
    private String message = "An unknown error occurred.";
    @Expose
    private int code;
    @Expose
    @JsonProperty("trace_id")
    @SerializedName("trace_id")
    private String traceId;

    public ApplicationError() {

    }

    public ApplicationError(String message) {
        this.message = message;
    }

    public ApplicationError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
