package com.isapp.isstudentapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUpdationCheckModel {

    public AppUpdationCheckModel(Authentication authentication, RequestData requestData) {
        this.authentication = authentication;
        this.requestData = requestData;
    }

    @SerializedName("authentication")
    @Expose
    private Authentication authentication;
    @SerializedName("requestData")
    @Expose
    private RequestData requestData;
    @SerializedName("statusResponse")
    @Expose
    private StatusResponse statusResponse;
    @SerializedName("appversionUpdateResponse")
    @Expose
    private AppversionUpdateResponse appversionUpdateResponse;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public StatusResponse getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(StatusResponse statusResponse) {
        this.statusResponse = statusResponse;
    }

    public AppversionUpdateResponse getAppversionUpdateResponse() {
        return appversionUpdateResponse;
    }

    public void setAppversionUpdateResponse(AppversionUpdateResponse appversionUpdateResponse) {
        this.appversionUpdateResponse = appversionUpdateResponse;
    }

    public class AppversionUpdateResponse {

        @SerializedName("versionUpdateStatus")
        @Expose
        private String versionUpdateStatus;
        @SerializedName("versionCode")
        @Expose
        private String versionCode;



        public String getVersionUpdateStatus() {
            return versionUpdateStatus;
        }

        public void setVersionUpdateStatus(String versionUpdateStatus) {
            this.versionUpdateStatus = versionUpdateStatus;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

    }

    public static class Authentication {

        @SerializedName("hash")
        @Expose
        private String hash;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("userType")
        @Expose
        private String userType;

        public Authentication(String hash, String userId, String userType) {
            this.hash = hash;
            this.userId = userId;
            this.userType = userType;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

    }
    public static class RequestData {

        @SerializedName("version")
        @Expose
        private String version;

        public RequestData(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }

    public class StatusResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("userType")
        @Expose
        private Object userType;
        @SerializedName("statusCode")
        @Expose
        private String statusCode;
        @SerializedName("message")
        @Expose
        private Object message;

        public StatusResponse() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

    }

}