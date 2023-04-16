package com.isapp.isstudentapp.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("userId")
    private int userId;
    @SerializedName("gradeName")
    private String gradeName;
    @SerializedName("parentName")
    private String parentName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @SerializedName("countryName")
    private String countryName;


    @SerializedName("cityName")
    private String cityName;
    @SerializedName("name")
    private String name;
    @SerializedName("rollNumber")
    private String rollNumber;
    @SerializedName("contactNumber")
    private String contactNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("picture")
    private String pictureLink;
    @SerializedName("addmissionDate")
    private String addmissionDate;
    @SerializedName("status")
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getAddmissionDate() {
        return addmissionDate;
    }

    public void setAddmissionDate(String addmissionDate) {
        this.addmissionDate = addmissionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserProfile(int userId) {
        this.userId = userId;
    }


}
