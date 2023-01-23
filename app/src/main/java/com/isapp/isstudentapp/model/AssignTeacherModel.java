package com.isapp.isstudentapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignTeacherModel {

    public Integer getStudentStandardId() {
        return studentStandardId;
    }

    public void setStudentStandardId(Integer studentStandardId) {
        this.studentStandardId = studentStandardId;
    }

    @SerializedName("studentStandardId")
    @Expose
    private Integer studentStandardId;

    public AssignTeacherModel(Integer studentStandardId) {
        this.studentStandardId = studentStandardId;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("assignedTeachers")
    @Expose
    private List<AssignedTeacher> assignedTeachers = null;



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

    public List<AssignedTeacher> getAssignedTeachers() {
        return assignedTeachers;
    }

    public void setAssignedTeachers(List<AssignedTeacher> assignedTeachers) {
        this.assignedTeachers = assignedTeachers;
    }

    public class AssignedTeacher {


        @SerializedName("teacherId")
        @Expose
        private String teacherId;

        @SerializedName("teacherName")
        @Expose
        private String teacherName;
        @SerializedName("studentName")
        @Expose
        private String studentName;
        @SerializedName("coursesName")
        @Expose
        private String coursesName;

        @SerializedName("teacherEmail")
        @Expose
        private String teacherEmail;

        public String getTeacherEmail() {
            return teacherEmail;
        }

        public void setTeacherEmail(String teacherEmail) {
            this.teacherEmail = teacherEmail;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCoursesName() {
            return coursesName;
        }

        public void setCoursesName(String coursesName) {
            this.coursesName = coursesName;
        }

    }


}