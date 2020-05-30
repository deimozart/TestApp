package com.arturogonzalez.testapp.utils.employee;

import com.arturogonzalez.testapp.response.EmployeeResponse;
import com.arturogonzalez.testapp.response.EmployeesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeAPI {

    @GET("/api/v1/employees")
    Call<EmployeesResponse> getAllEmployees();

   @GET ("/api/v1/employee/{employeeId}")
    Call<EmployeeResponse> getEmployee(@Path("employeeId") String employeeId);

}
