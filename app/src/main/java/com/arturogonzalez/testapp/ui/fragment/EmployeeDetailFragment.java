package com.arturogonzalez.testapp.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arturogonzalez.testapp.R;
import com.arturogonzalez.testapp.data.EmployeeController;
import com.arturogonzalez.testapp.model.Employee;
import com.arturogonzalez.testapp.ui.activity.MainActivity;
import com.arturogonzalez.testapp.utils.employee.EmployeeWSInterface;

public class EmployeeDetailFragment extends Fragment implements EmployeeWSInterface {

    private Employee employee;

    private Employee employeeAux;
    private TextView idTxt,nameTxt,salaryTxt,ageTxt;
    private ProgressDialog progressDialog;
    private String employeeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_employee_fragment,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = savedInstanceState!=null?savedInstanceState:getArguments();
        if (bundle != null){
            employeeId = bundle.getString(getString(R.string.employee_id_bundle_detail));
            employeeAux = bundle.getParcelable(getString(R.string.employee_bundle_detail));
        }
        idTxt = (TextView) getActivity().findViewById(R.id.employee_id_detail);
        nameTxt = (TextView) getActivity().findViewById(R.id.employee_name_detail);
        ageTxt = (TextView) getActivity().findViewById(R.id.employee_age_detail);
        salaryTxt = (TextView) getActivity().findViewById(R.id.employee_salary_detail);

    }


    public void configureView(){
        idTxt.setText(employee.getId());
        nameTxt.setText(employee.getName());
        ageTxt.setText(employee.getAge());
        salaryTxt.setText(employee.getSalary());
        if (employee.validateColorSalary()){
            salaryTxt.setTextColor(getResources().getColor(R.color.green));
        }else{
            salaryTxt.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setTitle(R.string.employee_detail_title);
        getEmployee(employeeId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
    }

    private void getEmployee(String employeeId){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        EmployeeController.getEmployee(employeeId,this);
    }

    public void somethingWasWrong(){
        Toast.makeText(getContext(), R.string.somethingWrongLastEmployee, Toast.LENGTH_SHORT).show();
        employee = employeeAux;
        configureView();
    }


    @Override
    public void getEmployeeFailure() {
        progressDialog.dismiss();
        somethingWasWrong();
    }

    @Override
    public void getEmployeeSuccess(Employee employee) {
        progressDialog.dismiss();
        this.employee = employee;
        configureView();
    }
}
