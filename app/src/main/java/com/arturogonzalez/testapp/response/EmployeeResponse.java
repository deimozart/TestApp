package com.arturogonzalez.testapp.response;

import com.arturogonzalez.testapp.model.Employee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Employee data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }

    public EmployeeResponse withData(Employee data) {
        this.data = data;
        return this;
    }
}