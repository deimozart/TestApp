package com.arturogonzalez.testapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arturogonzalez.testapp.R;
import com.arturogonzalez.testapp.model.Employee;
import com.arturogonzalez.testapp.ui.activity.MainActivity;

import java.util.ArrayList;

public class EmployeeListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayAdapter<Employee> employeeAdapter;
    private ArrayList<Employee> employees;
    private ListView employeeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_list_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        employeeList = (ListView) getActivity().findViewById(R.id.employee_list);
        employeeList.setOnItemClickListener(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        employees = bundle.getParcelableArrayList(getString(R.string.employee_list_bundle));
    }

    @Override
     public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setTitle(R.string.employee_title);
        employeeAdapter = new ArrayAdapter<Employee>(getActivity(),R.layout.employee_view,employees){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                RelativeLayout item;
                if (convertView == null) {

                    item =(RelativeLayout) getLayoutInflater().inflate(R.layout.employee_view, parent, false);
                    convertView = item;
                } else {
                    item = (RelativeLayout) convertView;
                }
                TextView ageTxt =(TextView) item.findViewById(R.id.employee_age);
                TextView idTxt =(TextView) item.findViewById(R.id.employee_ID);
                TextView nameTxt =(TextView) item.findViewById(R.id.employee_name);
                TextView salaryTxt =(TextView) item.findViewById(R.id.employee_salary);
                Employee employee = employees.get(position);
                nameTxt.setText(employee.getName());
                salaryTxt.setText(employee.getSalary());
                idTxt.setText(employee.getId());
                ageTxt.setText(employee.getAge());
                if (employee.validateColorAge()){
                    ageTxt.setTextColor(getResources().getColor(R.color.green));
                }else{
                    ageTxt.setTextColor(getResources().getColor(R.color.red));
                }
                return convertView;
            }
        };
        employeeList.setAdapter(employeeAdapter);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Employee employee = employees.get(position);
        Fragment fragment = new EmployeeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.employee_id_bundle_detail),employee.getId());
        bundle.putParcelable(getString(R.string.employee_bundle_detail),employee);
        fragment.setArguments(bundle);
        ((MainActivity) getActivity()).showFragment(fragment, true);
    }
}
