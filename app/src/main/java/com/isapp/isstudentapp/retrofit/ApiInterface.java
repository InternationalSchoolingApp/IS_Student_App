package com.isapp.isstudentapp.retrofit;

import com.isapp.isstudentapp.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("forgot-password")
    Call<ForgetPasswordModel> forgetPassword(@Body ForgetPasswordModel forgetPasswordModel);

    @POST("login-platform-App")
    Call<LoginModel> loginPostData(@Body LoginModel loginModel);

    @POST("device-token")
    Call<FirebaseTokenModel> firebaseToken(@Body FirebaseTokenModel firebaseTokenModel);

    @POST("notification-check")
    Call<DashboardNotificationModel> dashboardNotificationModel(@Body DashboardNotificationModel dashboardNotificationModel);

    @POST("create-my-notes")
    Call<CreateMyNotes> createMyNotes(@Body CreateMyNotes createMyNotes);

    @POST("get-my-notes")
    Call<GetNotes> getMyNotes(@Body GetNotes getNotes);


    @POST("teacher-image")
    Call<TeacherInfoModel> teacherInfoModel(@Body TeacherInfoModel teacherInfoModel);

    @POST("get-student-mapped-teacher")
    Call<AssignTeacherModel> assignTeacherChat(@Body AssignTeacherModel assignTeacherModel);

    @POST("school-calendar")
    Call<ScheduleModel> getSchedule(@Body ScheduleModel scheduleModel);

    @POST("get-student-profile-view-app")
    Call<UserProfile> profilePostData(@Body UserProfile userProfile);

    @POST("logout-platform-App")
    Call<LogoutModel> logoutPostData(@Body LogoutModel logoutModel);


    @POST("dashboardApp")
    Call<DashBoardModel> dashboardPostData(@Body DashBoardModel dashBoardModel);

    @POST("get-notification-app")
    Call<NotificationForApp> notificationForApp(@Body NotificationForApp notificationForApp);

    @POST("get-student-progress-report")
    Call<SubjectListPerformance> progressSubjectList(@Body SubjectListPerformance subjectListPerformance);

    @POST("get-student-progress-report-detail")
    Call<SubjectPerformanceDetail> progressBySubject(@Body SubjectPerformanceDetail subjectListPerformance);

}
