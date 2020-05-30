package com.arturogonzalez.testapp.utils.employee;

import com.arturogonzalez.testapp.model.Employee;

import java.util.List;

public interface EmployeesWSInterface {
    void getAllEmployeesFailure();
    void getAllEmployeesSuccess(List<Employee> list);
}
