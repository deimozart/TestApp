package com.arturogonzalez.testapp.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arturogonzalez.testapp.R;
import com.arturogonzalez.testapp.data.EmployeeController;
import com.arturogonzalez.testapp.model.Employee;
import com.arturogonzalez.testapp.utils.employee.EmployeesWSInterface;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, EmployeesWSInterface {

    private ProgressDialog progressDialog;
    private EditText idTxt;
    private Button loginButton;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relativeLayout =  findViewById(R.id.relative_layout_login);

        idTxt = findViewById(R.id.employee_id_login);
        loginButton = findViewById(R.id.input_button);
        loginButton.setOnClickListener(this);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void verifyEmployee(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EmployeeController.getAllEmployees(this);
    }
    public void somethingWasWrong(){
        Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

    }

    private void validateUser(List<Employee> data) {
        if (EmployeeController.validateEmployee(idTxt.getText().toString(),data)){
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.employee_list_bundle), new ArrayList<Employee>(data));
            bundle.putString(getString(R.string.employee_id_bundle),idTxt.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(LoginActivity.this, "It´s not possible localize the user", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (idTxt.getText().toString().trim().equals("")){
            Toast.makeText(LoginActivity.this, "It´s necessary set text in the input", Toast.LENGTH_SHORT).show();
        }else {
            verifyEmployee();
        }
    }

    @Override
    public void getAllEmployeesFailure() {
        progressDialog.dismiss();
        somethingWasWrong();
    }

    @Override
    public void getAllEmployeesSuccess(List<Employee> list) {
        validateUser(list);
    }
}
