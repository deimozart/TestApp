package com.arturogonzalez.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.arturogonzalez.testapp.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Employee implements Parcelable {



    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_salary")
    @Expose
    private String employeeSalary;
    @SerializedName("employee_age")
    @Expose
    private String employeeAge;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return employeeName;
    }

    public void setName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Employee withName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public String getSalary() {
        return employeeSalary;
    }

    public void setSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public Employee withSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
        return this;
    }

    public String getAge() {
        return employeeAge;
    }

    public void setAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public Employee withAge(String employeeAge) {
        this.employeeAge = employeeAge;
        return this;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Employee withProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeString(id);
        dest.writeString(employeeAge);
        dest.writeString(employeeName);
        dest.writeString(employeeSalary);
        dest.writeString(profileImage);
    }

    public Employee(Parcel in) {
        id = in.readString();
        employeeAge = in.readString();
        employeeName = in.readString();
        employeeSalary = in.readString();
        profileImage = in.readString();
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public boolean validateColorAge(){
        return Integer.parseInt( employeeAge) >25 && Integer.parseInt( employeeAge) < 35;
    }

    public boolean validateColorSalary(){
        return Integer.parseInt( employeeSalary) >1000;
    }
}
