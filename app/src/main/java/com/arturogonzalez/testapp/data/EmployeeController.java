package com.arturogonzalez.testapp.data;

import com.arturogonzalez.testapp.model.Employee;
import com.arturogonzalez.testapp.response.EmployeeResponse;
import com.arturogonzalez.testapp.response.EmployeesResponse;
import com.arturogonzalez.testapp.utils.employee.EmployeeAPI;
import com.arturogonzalez.testapp.utils.employee.EmployeeWSInterface;
import com.arturogonzalez.testapp.utils.employee.EmployeesWSInterface;
import com.arturogonzalez.testapp.utils.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeController  {

    public static boolean validateEmployee(String text, List<Employee> employeeList) {
        boolean result = false;
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getId().equals(text)){
                result = true;
                break;
            }
        }
        return result;
    }

    public static int positionOfEmployee(String text, List<Employee> employeeList) {
        int result = -1;
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getId().equals(text)){
                result = i;
                break;
            }
        }
        return result;
    }

    public static void getEmployee(String employeeId, final EmployeeWSInterface listener) {
        EmployeeAPI service = RetrofitClientInstance.getRetrofitInstance().create(EmployeeAPI.class);
        Call<EmployeeResponse> call = service.getEmployee(employeeId);
        call.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {

                if (!response.isSuccessful()){
                    listener.getEmployeeFailure();
                }else{
                    listener.getEmployeeSuccess(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                listener.getEmployeeFailure();
            }
        });

    }

    public static void getAllEmployees(final EmployeesWSInterface listener) {
        EmployeeAPI service = RetrofitClientInstance.getRetrofitInstance().create(EmployeeAPI.class);
        Call<EmployeesResponse> call = service.getAllEmployees();
        call.enqueue(new Callback<EmployeesResponse>() {
            @Override
            public void onResponse(Call<EmployeesResponse> call, Response<EmployeesResponse> response) {

                if (!response.isSuccessful()){
                    listener.getAllEmployeesFailure();
                }else {
                    listener.getAllEmployeesSuccess(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<EmployeesResponse> call, Throwable t) {
                listener.getAllEmployeesFailure();
            }
        });
    }
}
