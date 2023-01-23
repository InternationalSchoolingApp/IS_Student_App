package com.isapp.isstudentapp.model;

import com.google.gson.annotations.SerializedName;

public class TeacherInfoModel {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherInfoModel(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @SerializedName("teacherId")
    private Integer teacherId;
}
