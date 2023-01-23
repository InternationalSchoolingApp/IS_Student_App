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
