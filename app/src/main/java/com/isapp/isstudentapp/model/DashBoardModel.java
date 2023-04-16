package com.isapp.isstudentapp.model;

import com.google.gson.annotations.SerializedName;

public class DashBoardModel {
    @SerializedName("userId")
    private Integer userId;
    @SerializedName("firstName")
    private String fName;
    @SerializedName("studentStandardId")
    private String gradeName;
    @SerializedName("regType")
    private String regType;
    @SerializedName("status")
    private String status;




    @SerializedName("state")
    private String state;
    @SerializedName("city")
    private String city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @SerializedName("country")
    private String country;



    public DashBoardModel(Integer userId) {
        this.userId = userId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
