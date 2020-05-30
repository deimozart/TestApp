package com.arturogonzalez.testapp.utils.employee;

import com.arturogonzalez.testapp.model.Employee;

public interface EmployeeWSInterface {
    void getEmployeeFailure();
    void getEmployeeSuccess(Employee employee);
}
