

package com.isapp.isstudentapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectListPerformance {


    @SerializedName("userId")
    private Integer userId;

    public SubjectListPerformance(Integer userId) {
        this.userId = userId;
    }

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("totalFinalTime")
    @Expose
    private Object totalFinalTime;
    @SerializedName("domainStudentsList")
    @Expose
    private List<DomainStudents> domainStudentsList = null;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getTotalFinalTime() {
        return totalFinalTime;
    }

    public void setTotalFinalTime(Object totalFinalTime) {
        this.totalFinalTime = totalFinalTime;
    }

    public List<DomainStudents> getDomainStudentsList() {
        return domainStudentsList;
    }

    public void setDomainStudentsList(List<DomainStudents> domainStudentsList) {
        this.domainStudentsList = domainStudentsList;
    }



    public class DomainStudents {

        @SerializedName("studentId")
        @Expose
        private Integer studentId;
        @SerializedName("studentName")
        @Expose
        private String studentName;
        @SerializedName("endDate")
        @Expose
        private Object endDate;
        @SerializedName("domainStudent")
        @Expose
        private List<DomainStudent> domainStudent = null;

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public List<DomainStudent> getDomainStudent() {
            return domainStudent;
        }

        public void setDomainStudent(List<DomainStudent> domainStudent) {
            this.domainStudent = domainStudent;
        }

        public class DomainStudent {

            @SerializedName("domainId")
            @Expose
            private Object domainId;
            @SerializedName("domainName")
            @Expose
            private Object domainName;
            @SerializedName("studentId")
            @Expose
            private String studentId;
            @SerializedName("studentLmsId")
            @Expose
            private String studentLmsId;
            @SerializedName("stuFirstName")
            @Expose
            private Object stuFirstName;
            @SerializedName("stuLastName")
            @Expose
            private Object stuLastName;
            @SerializedName("totalRegister")
            @Expose
            private Object totalRegister;
            @SerializedName("totalTeacher")
            @Expose
            private Object totalTeacher;
            @SerializedName("totalStudent")
            @Expose
            private Object totalStudent;
            @SerializedName("studentName")
            @Expose
            private String studentName;
            @SerializedName("courseId")
            @Expose
            private String courseId;
            @SerializedName("courseName")
            @Expose
            private String courseName;
            @SerializedName("startDate")
            @Expose
            private Object startDate;
            @SerializedName("endDate")
            @Expose
            private Object endDate;
            @SerializedName("percentage")
            @Expose
            private Double percentage;
            @SerializedName("lastActivityDate")
            @Expose
            private Object lastActivityDate;
            @SerializedName("submitdate")
            @Expose
            private Object submitdate;
            @SerializedName("duedate")
            @Expose
            private Object duedate;
            @SerializedName("complete")
            @Expose
            private String complete;
            @SerializedName("completeAll")
            @Expose
            private String completeAll;
            @SerializedName("scoreDate")
            @Expose
            private Object scoreDate;
            @SerializedName("teacherId")
            @Expose
            private Object teacherId;
            @SerializedName("teacherName")
            @Expose
            private String teacherName;
            @SerializedName("teacherEmailId")
            @Expose
            private Object teacherEmailId;
            @SerializedName("activityName")
            @Expose
            private Object activityName;
            @SerializedName("achieved")
            @Expose
            private String achieved;

            public Object getDomainId() {
                return domainId;
            }

            public void setDomainId(Object domainId) {
                this.domainId = domainId;
            }

            public Object getDomainName() {
                return domainName;
            }

            public void setDomainName(Object domainName) {
                this.domainName = domainName;
            }

            public String getStudentId() {
                return studentId;
            }

            public void setStudentId(String studentId) {
                this.studentId = studentId;
            }

            public String getStudentLmsId() {
                return studentLmsId;
            }

            public void setStudentLmsId(String studentLmsId) {
                this.studentLmsId = studentLmsId;
            }

            public Object getStuFirstName() {
                return stuFirstName;
            }

            public void setStuFirstName(Object stuFirstName) {
                this.stuFirstName = stuFirstName;
            }

            public Object getStuLastName() {
                return stuLastName;
            }

            public void setStuLastName(Object stuLastName) {
                this.stuLastName = stuLastName;
            }

            public Object getTotalRegister() {
                return totalRegister;
            }

            public void setTotalRegister(Object totalRegister) {
                this.totalRegister = totalRegister;
            }

            public Object getTotalTeacher() {
                return totalTeacher;
            }

            public void setTotalTeacher(Object totalTeacher) {
                this.totalTeacher = totalTeacher;
            }

            public Object getTotalStudent() {
                return totalStudent;
            }

            public void setTotalStudent(Object totalStudent) {
                this.totalStudent = totalStudent;
            }

            public String getStudentName() {
                return studentName;
            }

            public void setStudentName(String studentName) {
                this.studentName = studentName;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public Object getStartDate() {
                return startDate;
            }

            public void setStartDate(Object startDate) {
                this.startDate = startDate;
            }

            public Object getEndDate() {
                return endDate;
            }

            public void setEndDate(Object endDate) {
                this.endDate = endDate;
            }

            public Double getPercentage() {
                return percentage;
            }

            public void setPercentage(Double percentage) {
                this.percentage = percentage;
            }

            public Object getLastActivityDate() {
                return lastActivityDate;
            }

            public void setLastActivityDate(Object lastActivityDate) {
                this.lastActivityDate = lastActivityDate;
            }

            public Object getSubmitdate() {
                return submitdate;
            }

            public void setSubmitdate(Object submitdate) {
                this.submitdate = submitdate;
            }

            public Object getDuedate() {
                return duedate;
            }

            public void setDuedate(Object duedate) {
                this.duedate = duedate;
            }

            public String getComplete() {
                return complete;
            }

            public void setComplete(String complete) {
                this.complete = complete;
            }

            public String getCompleteAll() {
                return completeAll;
            }

            public void setCompleteAll(String completeAll) {
                this.completeAll = completeAll;
            }

            public Object getScoreDate() {
                return scoreDate;
            }

            public void setScoreDate(Object scoreDate) {
                this.scoreDate = scoreDate;
            }

            public Object getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(Object teacherId) {
                this.teacherId = teacherId;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public Object getTeacherEmailId() {
                return teacherEmailId;
            }

            public void setTeacherEmailId(Object teacherEmailId) {
                this.teacherEmailId = teacherEmailId;
            }

            public Object getActivityName() {
                return activityName;
            }

            public void setActivityName(Object activityName) {
                this.activityName = activityName;
            }

            public String getAchieved() {
                return achieved;
            }

            public void setAchieved(String achieved) {
                this.achieved = achieved;
            }


        }

    }


}




