package com.danielokoronkwo.employeemanager.common.dto;

import java.util.List;

public class ErrorMessageDto {
    private boolean status;
    private String message;
    private List<String> errors;

    private Object data = null;


    public ErrorMessageDto(boolean status, String message, List<String> errors, Object data) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
