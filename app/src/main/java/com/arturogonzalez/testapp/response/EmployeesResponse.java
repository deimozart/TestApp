package com.arturogonzalez.testapp.response;

import java.util.List;

import com.arturogonzalez.testapp.model.Employee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeesResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private List<Employee> data = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public EmployeesResponse withStatus(String status) {
            this.status = status;
            return this;
        }

        public List<Employee> getData() {
            return data;
        }

        public void setData(List<Employee> data) {
            this.data = data;
        }

        public EmployeesResponse withData(List<Employee> data) {
            this.data = data;
            return this;
        }
}

