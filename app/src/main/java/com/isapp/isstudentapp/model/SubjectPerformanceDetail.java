package com.isapp.isstudentapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SubjectPerformanceDetail {


    @SerializedName("subjectEntityId")
    @Expose
    private String subjectId;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("totalFinalTime")
    @Expose
    private Object totalFinalTime;
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("domainStudentsList")
    @Expose
    private List<DomainStudents> domainStudentsList = null;
    @SerializedName("totalAssignment")
    @Expose
    private Integer totalAssignment;
    @SerializedName("submiteAssign")
    @Expose
    private Integer submiteAssign;
    @SerializedName("upcomingAssign")
    @Expose
    private Integer upcomingAssign;
    @SerializedName("pendingAssign")
    @Expose
    private Integer pendingAssign;
    @SerializedName("excusedAssign")
    @Expose
    private Integer excusedAssign;
    @SerializedName("passesAssign")
    @Expose
    private Integer passesAssign;
    @SerializedName("failedAssign")
    @Expose
    private Integer failedAssign;
    @SerializedName("submitBeforeTimeAssign")
    @Expose
    private Integer submitBeforeTimeAssign;
    @SerializedName("submitOntimeAssign")
    @Expose
    private Integer submitOntimeAssign;
    @SerializedName("submitLateAssign")
    @Expose
    private Integer submitLateAssign;

    public SubjectPerformanceDetail(String subjectId, Integer userId) {
        this.subjectId = subjectId;
        this.userId = userId;
    }

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<DomainStudents> getDomainStudentsList() {
        return domainStudentsList;
    }

    public void setDomainStudentsList(List<DomainStudents> domainStudentsList) {
        this.domainStudentsList = domainStudentsList;
    }

    public Integer getTotalAssignment() {
        return totalAssignment;
    }

    public void setTotalAssignment(Integer totalAssignment) {
        this.totalAssignment = totalAssignment;
    }

    public Integer getSubmiteAssign() {
        return submiteAssign;
    }

    public void setSubmiteAssign(Integer submiteAssign) {
        this.submiteAssign = submiteAssign;
    }

    public Integer getUpcomingAssign() {
        return upcomingAssign;
    }

    public void setUpcomingAssign(Integer upcomingAssign) {
        this.upcomingAssign = upcomingAssign;
    }

    public Integer getPendingAssign() {
        return pendingAssign;
    }

    public void setPendingAssign(Integer pendingAssign) {
        this.pendingAssign = pendingAssign;
    }

    public Integer getExcusedAssign() {
        return excusedAssign;
    }

    public void setExcusedAssign(Integer excusedAssign) {
        this.excusedAssign = excusedAssign;
    }

    public Integer getPassesAssign() {
        return passesAssign;
    }

    public void setPassesAssign(Integer passesAssign) {
        this.passesAssign = passesAssign;
    }

    public Integer getFailedAssign() {
        return failedAssign;
    }

    public void setFailedAssign(Integer failedAssign) {
        this.failedAssign = failedAssign;
    }

    public Integer getSubmitBeforeTimeAssign() {
        return submitBeforeTimeAssign;
    }

    public void setSubmitBeforeTimeAssign(Integer submitBeforeTimeAssign) {
        this.submitBeforeTimeAssign = submitBeforeTimeAssign;
    }

    public Integer getSubmitOntimeAssign() {
        return submitOntimeAssign;
    }

    public void setSubmitOntimeAssign(Integer submitOntimeAssign) {
        this.submitOntimeAssign = submitOntimeAssign;
    }

    public Integer getSubmitLateAssign() {
        return submitLateAssign;
    }

    public void setSubmitLateAssign(Integer submitLateAssign) {
        this.submitLateAssign = submitLateAssign;
    }

    // Response class


    public class Response {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("message")
        @Expose
        private Object message;
        @SerializedName("enrollment")
        @Expose
        private Object enrollment;
        @SerializedName("enrollments")
        @Expose
        private Enrollments enrollments;
        @SerializedName("summary")
        @Expose
        private Object summary;
        @SerializedName("domains")
        @Expose
        private Object domains;
        @SerializedName("domainStudents")
        @Expose
        private Object domainStudents;
        @SerializedName("domainStudentList")
        @Expose
        private Object domainStudentList;
        @SerializedName("grades")
        @Expose
        private Object grades;

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

        public Object getEnrollment() {
            return enrollment;
        }

        public void setEnrollment(Object enrollment) {
            this.enrollment = enrollment;
        }

        public Enrollments getEnrollments() {
            return enrollments;
        }

        public void setEnrollments(Enrollments enrollments) {
            this.enrollments = enrollments;
        }

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public Object getDomains() {
            return domains;
        }

        public void setDomains(Object domains) {
            this.domains = domains;
        }

        public Object getDomainStudents() {
            return domainStudents;
        }

        public void setDomainStudents(Object domainStudents) {
            this.domainStudents = domainStudents;
        }

        public Object getDomainStudentList() {
            return domainStudentList;
        }

        public void setDomainStudentList(Object domainStudentList) {
            this.domainStudentList = domainStudentList;
        }

        public Object getGrades() {
            return grades;
        }

        public void setGrades(Object grades) {
            this.grades = grades;
        }

        // Enrollments Class

        public class Enrollments {

            @SerializedName("enrollment")
            @Expose
            private List<Enrollment> enrollment = null;

            public List<Enrollment> getEnrollment() {
                return enrollment;
            }

            public void setEnrollment(List<Enrollment> enrollment) {
                this.enrollment = enrollment;
            }

            //Enrollment Class


            public class Enrollment {

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("userid")
                @Expose
                private String userid;
                @SerializedName("entityid")
                @Expose
                private String entityid;
                @SerializedName("courseid")
                @Expose
                private Object courseid;
                @SerializedName("domainid")
                @Expose
                private String domainid;
                @SerializedName("roleid")
                @Expose
                private Object roleid;
                @SerializedName("reference")
                @Expose
                private String reference;
                @SerializedName("guid")
                @Expose
                private String guid;
                @SerializedName("flags")
                @Expose
                private String flags;
                @SerializedName("status")
                @Expose
                private String status;
                @SerializedName("startdate")
                @Expose
                private String startdate;
                @SerializedName("enddate")
                @Expose
                private String enddate;
                @SerializedName("achieved")
                @Expose
                private String achieved;
                @SerializedName("possible")
                @Expose
                private String possible;
                @SerializedName("failing")
                @Expose
                private String failing;
                @SerializedName("firstactivitydate")
                @Expose
                private String firstactivitydate;
                @SerializedName("lastactivitydate")
                @Expose
                private String lastactivitydate;
                @SerializedName("data")
                @Expose
                private Data data;
                @SerializedName("user")
                @Expose
                private Object user;
                @SerializedName("entity")
                @Expose
                private Entity entity;
                @SerializedName("domain")
                @Expose
                private Domain domain;
                @SerializedName("grades")
                @Expose
                private Grades grades;
                @SerializedName("course")
                @Expose
                private Object course;
                @SerializedName("enrollmentmetrics")
                @Expose
                private Object enrollmentmetrics;
                @SerializedName("teacherName")
                @Expose
                private Object teacherName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUserid() {
                    return userid;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
                }

                public String getEntityid() {
                    return entityid;
                }

                public void setEntityid(String entityid) {
                    this.entityid = entityid;
                }

                public Object getCourseid() {
                    return courseid;
                }

                public void setCourseid(Object courseid) {
                    this.courseid = courseid;
                }

                public String getDomainid() {
                    return domainid;
                }

                public void setDomainid(String domainid) {
                    this.domainid = domainid;
                }

                public Object getRoleid() {
                    return roleid;
                }

                public void setRoleid(Object roleid) {
                    this.roleid = roleid;
                }

                public String getReference() {
                    return reference;
                }

                public void setReference(String reference) {
                    this.reference = reference;
                }

                public String getGuid() {
                    return guid;
                }

                public void setGuid(String guid) {
                    this.guid = guid;
                }

                public String getFlags() {
                    return flags;
                }

                public void setFlags(String flags) {
                    this.flags = flags;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getStartdate() {
                    return startdate;
                }

                public void setStartdate(String startdate) {
                    this.startdate = startdate;
                }

                public String getEnddate() {
                    return enddate;
                }

                public void setEnddate(String enddate) {
                    this.enddate = enddate;
                }

                public String getAchieved() {
                    return achieved;
                }

                public void setAchieved(String achieved) {
                    this.achieved = achieved;
                }

                public String getPossible() {
                    return possible;
                }

                public void setPossible(String possible) {
                    this.possible = possible;
                }

                public String getFailing() {
                    return failing;
                }

                public void setFailing(String failing) {
                    this.failing = failing;
                }

                public String getFirstactivitydate() {
                    return firstactivitydate;
                }

                public void setFirstactivitydate(String firstactivitydate) {
                    this.firstactivitydate = firstactivitydate;
                }

                public String getLastactivitydate() {
                    return lastactivitydate;
                }

                public void setLastactivitydate(String lastactivitydate) {
                    this.lastactivitydate = lastactivitydate;
                }

                public Data getData() {
                    return data;
                }

                public void setData(Data data) {
                    this.data = data;
                }

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
                    this.user = user;
                }

                public Entity getEntity() {
                    return entity;
                }

                public void setEntity(Entity entity) {
                    this.entity = entity;
                }

                public Domain getDomain() {
                    return domain;
                }

                public void setDomain(Domain domain) {
                    this.domain = domain;
                }

                public Grades getGrades() {
                    return grades;
                }

                public void setGrades(Grades grades) {
                    this.grades = grades;
                }

                public Object getCourse() {
                    return course;
                }

                public void setCourse(Object course) {
                    this.course = course;
                }

                public Object getEnrollmentmetrics() {
                    return enrollmentmetrics;
                }

                public void setEnrollmentmetrics(Object enrollmentmetrics) {
                    this.enrollmentmetrics = enrollmentmetrics;
                }

                public Object getTeacherName() {
                    return teacherName;
                }

                public void setTeacherName(Object teacherName) {
                    this.teacherName = teacherName;
                }

                // Data Class

                public class Data {

                    @SerializedName("status")
                    @Expose
                    private Status status;

                    public Status getStatus() {
                        return status;
                    }

                    public void setStatus(Status status) {
                        this.status = status;
                    }

                    // Status Class

                    public class Status {

                        @SerializedName("performance")
                        @Expose
                        private Performance performance;
                        @SerializedName("pace")
                        @Expose
                        private Pace pace;

                        public Performance getPerformance() {
                            return performance;
                        }

                        public void setPerformance(Performance performance) {
                            this.performance = performance;
                        }

                        public Pace getPace() {
                            return pace;
                        }

                        public void setPace(Pace pace) {
                            this.pace = pace;
                        }

                        // Performance Class & Pace Class

                        public class Performance {

                            @SerializedName("signal")
                            @Expose
                            private String signal;
                            @SerializedName("code")
                            @Expose
                            private Object code;
                            @SerializedName("limit1")
                            @Expose
                            private Object limit1;
                            @SerializedName("limit2")
                            @Expose
                            private Object limit2;

                            public String getSignal() {
                                return signal;
                            }

                            public void setSignal(String signal) {
                                this.signal = signal;
                            }

                            public Object getCode() {
                                return code;
                            }

                            public void setCode(Object code) {
                                this.code = code;
                            }

                            public Object getLimit1() {
                                return limit1;
                            }

                            public void setLimit1(Object limit1) {
                                this.limit1 = limit1;
                            }

                            public Object getLimit2() {
                                return limit2;
                            }

                            public void setLimit2(Object limit2) {
                                this.limit2 = limit2;
                            }

                        }

                        public class Pace {

                            @SerializedName("signal")
                            @Expose
                            private String signal;
                            @SerializedName("code")
                            @Expose
                            private Object code;
                            @SerializedName("message")
                            @Expose
                            private Object message;
                            @SerializedName("limit1")
                            @Expose
                            private Object limit1;

                            public String getSignal() {
                                return signal;
                            }

                            public void setSignal(String signal) {
                                this.signal = signal;
                            }

                            public Object getCode() {
                                return code;
                            }

                            public void setCode(Object code) {
                                this.code = code;
                            }

                            public Object getMessage() {
                                return message;
                            }

                            public void setMessage(Object message) {
                                this.message = message;
                            }

                            public Object getLimit1() {
                                return limit1;
                            }

                            public void setLimit1(Object limit1) {
                                this.limit1 = limit1;
                            }

                        }

                    }


                }

                // Entity Class

                public class Entity {

                    @SerializedName("id")
                    @Expose
                    private Integer id;
                    @SerializedName("entitytype")
                    @Expose
                    private String entitytype;
                    @SerializedName("title")
                    @Expose
                    private String title;
                    @SerializedName("reference")
                    @Expose
                    private String reference;
                    @SerializedName("guid")
                    @Expose
                    private String guid;
                    @SerializedName("domainid")
                    @Expose
                    private String domainid;
                    @SerializedName("schema")
                    @Expose
                    private String schema;
                    @SerializedName("protection")
                    @Expose
                    private String protection;
                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("startdate")
                    @Expose
                    private String startdate;
                    @SerializedName("enddate")
                    @Expose
                    private String enddate;
                    @SerializedName("days")
                    @Expose
                    private String days;
                    @SerializedName("term")
                    @Expose
                    private String term;
                    @SerializedName("baseid")
                    @Expose
                    private String baseid;

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                    }

                    public String getEntitytype() {
                        return entitytype;
                    }

                    public void setEntitytype(String entitytype) {
                        this.entitytype = entitytype;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getReference() {
                        return reference;
                    }

                    public void setReference(String reference) {
                        this.reference = reference;
                    }

                    public String getGuid() {
                        return guid;
                    }

                    public void setGuid(String guid) {
                        this.guid = guid;
                    }

                    public String getDomainid() {
                        return domainid;
                    }

                    public void setDomainid(String domainid) {
                        this.domainid = domainid;
                    }

                    public String getSchema() {
                        return schema;
                    }

                    public void setSchema(String schema) {
                        this.schema = schema;
                    }

                    public String getProtection() {
                        return protection;
                    }

                    public void setProtection(String protection) {
                        this.protection = protection;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getStartdate() {
                        return startdate;
                    }

                    public void setStartdate(String startdate) {
                        this.startdate = startdate;
                    }

                    public String getEnddate() {
                        return enddate;
                    }

                    public void setEnddate(String enddate) {
                        this.enddate = enddate;
                    }

                    public String getDays() {
                        return days;
                    }

                    public void setDays(String days) {
                        this.days = days;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public String getBaseid() {
                        return baseid;
                    }

                    public void setBaseid(String baseid) {
                        this.baseid = baseid;
                    }

                }

                // Domain Class

                public class Domain {

                    @SerializedName("id")
                    @Expose
                    private Integer id;
                    @SerializedName("name")
                    @Expose
                    private String name;

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                }

                // Grade Class

                public class Grades {

                    @SerializedName("achieved")
                    @Expose
                    private String achieved;
                    @SerializedName("possible")
                    @Expose
                    private String possible;
                    @SerializedName("letter")
                    @Expose
                    private String letter;
                    @SerializedName("passingscore")
                    @Expose
                    private String passingscore;
                    @SerializedName("complete")
                    @Expose
                    private String complete;
                    @SerializedName("seconds")
                    @Expose
                    private String seconds;
                    @SerializedName("completedgradable")
                    @Expose
                    private String completedgradable;
                    @SerializedName("gradable")
                    @Expose
                    private String gradable;
                    @SerializedName("completeall")
                    @Expose
                    private String completeall;
                    @SerializedName("completable")
                    @Expose
                    private String completable;
                    @SerializedName("completed")
                    @Expose
                    private String completed;
                    @SerializedName("percentage")
                    @Expose
                    private String percentage;
                    @SerializedName("pointEarned")
                    @Expose
                    private Object pointEarned;
                    @SerializedName("categories")
                    @Expose
                    private Categories categories;
                    @SerializedName("items")
                    @Expose
                    private Items items;
                    @SerializedName("finals")
                    @Expose
                    private Object finals;
                    @SerializedName("totalDays")
                    @Expose
                    private Object totalDays;
                    @SerializedName("itemTrue")
                    @Expose
                    private Boolean itemTrue;
                    @SerializedName("unitStatus")
                    @Expose
                    private Boolean unitStatus;
                    @SerializedName("totalAssignment")
                    @Expose
                    private Object totalAssignment;
                    @SerializedName("submiteAssign")
                    @Expose
                    private Object submiteAssign;
                    @SerializedName("upcomingAssign")
                    @Expose
                    private Object upcomingAssign;
                    @SerializedName("pendingAssign")
                    @Expose
                    private Object pendingAssign;
                    @SerializedName("excusedAssign")
                    @Expose
                    private Object excusedAssign;
                    @SerializedName("passesAssign")
                    @Expose
                    private Object passesAssign;
                    @SerializedName("failedAssign")
                    @Expose
                    private Object failedAssign;
                    @SerializedName("submitBeforeTimeAssign")
                    @Expose
                    private Object submitBeforeTimeAssign;
                    @SerializedName("submitOntimeAssign")
                    @Expose
                    private Object submitOntimeAssign;
                    @SerializedName("submitLateAssign")
                    @Expose
                    private Object submitLateAssign;

                    public String getAchieved() {
                        return achieved;
                    }

                    public void setAchieved(String achieved) {
                        this.achieved = achieved;
                    }

                    public String getPossible() {
                        return possible;
                    }

                    public void setPossible(String possible) {
                        this.possible = possible;
                    }

                    public String getLetter() {
                        return letter;
                    }

                    public void setLetter(String letter) {
                        this.letter = letter;
                    }

                    public String getPassingscore() {
                        return passingscore;
                    }

                    public void setPassingscore(String passingscore) {
                        this.passingscore = passingscore;
                    }

                    public String getComplete() {
                        return complete;
                    }

                    public void setComplete(String complete) {
                        this.complete = complete;
                    }

                    public String getSeconds() {
                        return seconds;
                    }

                    public void setSeconds(String seconds) {
                        this.seconds = seconds;
                    }

                    public String getCompletedgradable() {
                        return completedgradable;
                    }

                    public void setCompletedgradable(String completedgradable) {
                        this.completedgradable = completedgradable;
                    }

                    public String getGradable() {
                        return gradable;
                    }

                    public void setGradable(String gradable) {
                        this.gradable = gradable;
                    }

                    public String getCompleteall() {
                        return completeall;
                    }

                    public void setCompleteall(String completeall) {
                        this.completeall = completeall;
                    }

                    public String getCompletable() {
                        return completable;
                    }

                    public void setCompletable(String completable) {
                        this.completable = completable;
                    }

                    public String getCompleted() {
                        return completed;
                    }

                    public void setCompleted(String completed) {
                        this.completed = completed;
                    }

                    public String getPercentage() {
                        return percentage;
                    }

                    public void setPercentage(String percentage) {
                        this.percentage = percentage;
                    }

                    public Object getPointEarned() {
                        return pointEarned;
                    }

                    public void setPointEarned(Object pointEarned) {
                        this.pointEarned = pointEarned;
                    }

                    public Categories getCategories() {
                        return categories;
                    }

                    public void setCategories(Categories categories) {
                        this.categories = categories;
                    }

                    public Items getItems() {
                        return items;
                    }

                    public void setItems(Items items) {
                        this.items = items;
                    }

                    public Object getFinals() {
                        return finals;
                    }

                    public void setFinals(Object finals) {
                        this.finals = finals;
                    }

                    public Object getTotalDays() {
                        return totalDays;
                    }

                    public void setTotalDays(Object totalDays) {
                        this.totalDays = totalDays;
                    }

                    public Boolean getItemTrue() {
                        return itemTrue;
                    }

                    public void setItemTrue(Boolean itemTrue) {
                        this.itemTrue = itemTrue;
                    }

                    public Boolean getUnitStatus() {
                        return unitStatus;
                    }

                    public void setUnitStatus(Boolean unitStatus) {
                        this.unitStatus = unitStatus;
                    }

                    public Object getTotalAssignment() {
                        return totalAssignment;
                    }

                    public void setTotalAssignment(Object totalAssignment) {
                        this.totalAssignment = totalAssignment;
                    }

                    public Object getSubmiteAssign() {
                        return submiteAssign;
                    }

                    public void setSubmiteAssign(Object submiteAssign) {
                        this.submiteAssign = submiteAssign;
                    }

                    public Object getUpcomingAssign() {
                        return upcomingAssign;
                    }

                    public void setUpcomingAssign(Object upcomingAssign) {
                        this.upcomingAssign = upcomingAssign;
                    }

                    public Object getPendingAssign() {
                        return pendingAssign;
                    }

                    public void setPendingAssign(Object pendingAssign) {
                        this.pendingAssign = pendingAssign;
                    }

                    public Object getExcusedAssign() {
                        return excusedAssign;
                    }

                    public void setExcusedAssign(Object excusedAssign) {
                        this.excusedAssign = excusedAssign;
                    }

                    public Object getPassesAssign() {
                        return passesAssign;
                    }

                    public void setPassesAssign(Object passesAssign) {
                        this.passesAssign = passesAssign;
                    }

                    public Object getFailedAssign() {
                        return failedAssign;
                    }

                    public void setFailedAssign(Object failedAssign) {
                        this.failedAssign = failedAssign;
                    }

                    public Object getSubmitBeforeTimeAssign() {
                        return submitBeforeTimeAssign;
                    }

                    public void setSubmitBeforeTimeAssign(Object submitBeforeTimeAssign) {
                        this.submitBeforeTimeAssign = submitBeforeTimeAssign;
                    }

                    public Object getSubmitOntimeAssign() {
                        return submitOntimeAssign;
                    }

                    public void setSubmitOntimeAssign(Object submitOntimeAssign) {
                        this.submitOntimeAssign = submitOntimeAssign;
                    }

                    public Object getSubmitLateAssign() {
                        return submitLateAssign;
                    }

                    public void setSubmitLateAssign(Object submitLateAssign) {
                        this.submitLateAssign = submitLateAssign;
                    }

                    // Categories Class

                    public class Categories {

                        @SerializedName("category")
                        @Expose
                        private List<Category> category = null;

                        public List<Category> getCategory() {
                            return category;
                        }

                        public void setCategory(List<Category> category) {
                            this.category = category;
                        }

                        public class Category {

                            @SerializedName("id")
                            @Expose
                            private String id;
                            @SerializedName("name")
                            @Expose
                            private String name;
                            @SerializedName("achieved")
                            @Expose
                            private String achieved;
                            @SerializedName("possible")
                            @Expose
                            private String possible;
                            @SerializedName("letter")
                            @Expose
                            private String letter;
                            @SerializedName("gradeview")
                            @Expose
                            private String gradeview;
                            @SerializedName("seconds")
                            @Expose
                            private String seconds;

                            public String getId() {
                                return id;
                            }

                            public void setId(String id) {
                                this.id = id;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getAchieved() {
                                return achieved;
                            }

                            public void setAchieved(String achieved) {
                                this.achieved = achieved;
                            }

                            public String getPossible() {
                                return possible;
                            }

                            public void setPossible(String possible) {
                                this.possible = possible;
                            }

                            public String getLetter() {
                                return letter;
                            }

                            public void setLetter(String letter) {
                                this.letter = letter;
                            }

                            public String getGradeview() {
                                return gradeview;
                            }

                            public void setGradeview(String gradeview) {
                                this.gradeview = gradeview;
                            }

                            public String getSeconds() {
                                return seconds;
                            }

                            public void setSeconds(String seconds) {
                                this.seconds = seconds;
                            }

                        }

                    }

                    // Class Items

                    public class Items {

                        @SerializedName("item")
                        @Expose
                        private List<Item> item = null;

                        public List<Item> getItem() {
                            return item;
                        }

                        public void setItem(List<Item> item) {
                            this.item = item;
                        }


                        public class Item {

                            @SerializedName("itemid")
                            @Expose
                            private String itemid;
                            @SerializedName("achieved")
                            @Expose
                            private String achieved;
                            @SerializedName("possible")
                            @Expose
                            private String possible;
                            @SerializedName("rawachieved")
                            @Expose
                            private Object rawachieved;
                            @SerializedName("rawpossible")
                            @Expose
                            private Object rawpossible;
                            @SerializedName("score")
                            @Expose
                            private Object score;
                            @SerializedName("scored")
                            @Expose
                            private Object scored;
                            @SerializedName("unsubmitted")
                            @Expose
                            private Object unsubmitted;
                            @SerializedName("failing")
                            @Expose
                            private Object failing;
                            @SerializedName("seconds")
                            @Expose
                            private String seconds;
                            @SerializedName("minutes")
                            @Expose
                            private Object minutes;
                            @SerializedName("graded")
                            @Expose
                            private Object graded;
                            @SerializedName("hasseconds")
                            @Expose
                            private Object hasseconds;
                            @SerializedName("hasminutes")
                            @Expose
                            private Object hasminutes;
                            @SerializedName("completed")
                            @Expose
                            private Object completed;
                            @SerializedName("title")
                            @Expose
                            private String title;
                            @SerializedName("periodid")
                            @Expose
                            private String periodid;
                            @SerializedName("categoryid")
                            @Expose
                            private String categoryid;
                            @SerializedName("parentid")
                            @Expose
                            private String parentid;
                            @SerializedName("type")
                            @Expose
                            private String type;
                            @SerializedName("gradable")
                            @Expose
                            private Boolean gradable;
                            @SerializedName("responseversion")
                            @Expose
                            private String responseversion;
                            @SerializedName("status")
                            @Expose
                            private String status;
                            @SerializedName("scoredversion")
                            @Expose
                            private String scoredversion;
                            @SerializedName("scoreddate")
                            @Expose
                            private String scoreddate;
                            @SerializedName("letter")
                            @Expose
                            private String letter;
                            @SerializedName("attempts")
                            @Expose
                            private String attempts;
                            @SerializedName("firstactivitydate")
                            @Expose
                            private String firstactivitydate;
                            @SerializedName("lastactivitydate")
                            @Expose
                            private String lastactivitydate;
                            @SerializedName("submittedversion")
                            @Expose
                            private String submittedversion;
                            @SerializedName("submitteddate")
                            @Expose
                            private String submitteddate;
                            @SerializedName("duedate")
                            @Expose
                            private String duedate;
                            @SerializedName("pacedate")
                            @Expose
                            private String pacedate;
                            @SerializedName("passing")
                            @Expose
                            private Boolean passing;
                            @SerializedName("unit")
                            @Expose
                            private String unit;
                            @SerializedName("unitTitle")
                            @Expose
                            private String unitTitle;
                            @SerializedName("unitType")
                            @Expose
                            private String unitType;
                            @SerializedName("unitTimeSpent")
                            @Expose
                            private String unitTimeSpent;
                            @SerializedName("unitPercent")
                            @Expose
                            private String unitPercent;
                            @SerializedName("colorDueText")
                            @Expose
                            private Object colorDueText;
                            @SerializedName("colorScoreText")
                            @Expose
                            private String colorScoreText;
                            @SerializedName("lateTime")
                            @Expose
                            private Object lateTime;
                            @SerializedName("submissionStatus")
                            @Expose
                            private String submissionStatus;
                            @SerializedName("duedats")
                            @Expose
                            private Long duedats;
                            @SerializedName("teacherGradeStatus")
                            @Expose
                            private String teacherGradeStatus;

                            public String getItemid() {
                                return itemid;
                            }

                            public void setItemid(String itemid) {
                                this.itemid = itemid;
                            }

                            public String getAchieved() {
                                return achieved;
                            }

                            public void setAchieved(String achieved) {
                                this.achieved = achieved;
                            }

                            public String getPossible() {
                                return possible;
                            }

                            public void setPossible(String possible) {
                                this.possible = possible;
                            }

                            public Object getRawachieved() {
                                return rawachieved;
                            }

                            public void setRawachieved(Object rawachieved) {
                                this.rawachieved = rawachieved;
                            }

                            public Object getRawpossible() {
                                return rawpossible;
                            }

                            public void setRawpossible(Object rawpossible) {
                                this.rawpossible = rawpossible;
                            }

                            public Object getScore() {
                                return score;
                            }

                            public void setScore(Object score) {
                                this.score = score;
                            }

                            public Object getScored() {
                                return scored;
                            }

                            public void setScored(Object scored) {
                                this.scored = scored;
                            }

                            public Object getUnsubmitted() {
                                return unsubmitted;
                            }

                            public void setUnsubmitted(Object unsubmitted) {
                                this.unsubmitted = unsubmitted;
                            }

                            public Object getFailing() {
                                return failing;
                            }

                            public void setFailing(Object failing) {
                                this.failing = failing;
                            }

                            public String getSeconds() {
                                return seconds;
                            }

                            public void setSeconds(String seconds) {
                                this.seconds = seconds;
                            }

                            public Object getMinutes() {
                                return minutes;
                            }

                            public void setMinutes(Object minutes) {
                                this.minutes = minutes;
                            }

                            public Object getGraded() {
                                return graded;
                            }

                            public void setGraded(Object graded) {
                                this.graded = graded;
                            }

                            public Object getHasseconds() {
                                return hasseconds;
                            }

                            public void setHasseconds(Object hasseconds) {
                                this.hasseconds = hasseconds;
                            }

                            public Object getHasminutes() {
                                return hasminutes;
                            }

                            public void setHasminutes(Object hasminutes) {
                                this.hasminutes = hasminutes;
                            }

                            public Object getCompleted() {
                                return completed;
                            }

                            public void setCompleted(Object completed) {
                                this.completed = completed;
                            }

                            public String getTitle() {
                                return title;
                            }

                            public void setTitle(String title) {
                                this.title = title;
                            }

                            public String getPeriodid() {
                                return periodid;
                            }

                            public void setPeriodid(String periodid) {
                                this.periodid = periodid;
                            }

                            public String getCategoryid() {
                                return categoryid;
                            }

                            public void setCategoryid(String categoryid) {
                                this.categoryid = categoryid;
                            }

                            public String getParentid() {
                                return parentid;
                            }

                            public void setParentid(String parentid) {
                                this.parentid = parentid;
                            }

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public Boolean getGradable() {
                                return gradable;
                            }

                            public void setGradable(Boolean gradable) {
                                this.gradable = gradable;
                            }

                            public String getResponseversion() {
                                return responseversion;
                            }

                            public void setResponseversion(String responseversion) {
                                this.responseversion = responseversion;
                            }

                            public String getStatus() {
                                return status;
                            }

                            public void setStatus(String status) {
                                this.status = status;
                            }

                            public String getScoredversion() {
                                return scoredversion;
                            }

                            public void setScoredversion(String scoredversion) {
                                this.scoredversion = scoredversion;
                            }

                            public String getScoreddate() {
                                return scoreddate;
                            }

                            public void setScoreddate(String scoreddate) {
                                this.scoreddate = scoreddate;
                            }

                            public String getLetter() {
                                return letter;
                            }

                            public void setLetter(String letter) {
                                this.letter = letter;
                            }

                            public String getAttempts() {
                                return attempts;
                            }

                            public void setAttempts(String attempts) {
                                this.attempts = attempts;
                            }

                            public String getFirstactivitydate() {
                                return firstactivitydate;
                            }

                            public void setFirstactivitydate(String firstactivitydate) {
                                this.firstactivitydate = firstactivitydate;
                            }

                            public String getLastactivitydate() {
                                return lastactivitydate;
                            }

                            public void setLastactivitydate(String lastactivitydate) {
                                this.lastactivitydate = lastactivitydate;
                            }

                            public String getSubmittedversion() {
                                return submittedversion;
                            }

                            public void setSubmittedversion(String submittedversion) {
                                this.submittedversion = submittedversion;
                            }

                            public String getSubmitteddate() {
                                return submitteddate;
                            }

                            public void setSubmitteddate(String submitteddate) {
                                this.submitteddate = submitteddate;
                            }

                            public String getDuedate() {
                                return duedate;
                            }

                            public void setDuedate(String duedate) {
                                this.duedate = duedate;
                            }

                            public String getPacedate() {
                                return pacedate;
                            }

                            public void setPacedate(String pacedate) {
                                this.pacedate = pacedate;
                            }

                            public Boolean getPassing() {
                                return passing;
                            }

                            public void setPassing(Boolean passing) {
                                this.passing = passing;
                            }

                            public String getUnit() {
                                return unit;
                            }

                            public void setUnit(String unit) {
                                this.unit = unit;
                            }

                            public String getUnitTitle() {
                                return unitTitle;
                            }

                            public void setUnitTitle(String unitTitle) {
                                this.unitTitle = unitTitle;
                            }

                            public String getUnitType() {
                                return unitType;
                            }

                            public void setUnitType(String unitType) {
                                this.unitType = unitType;
                            }

                            public String getUnitTimeSpent() {
                                return unitTimeSpent;
                            }

                            public void setUnitTimeSpent(String unitTimeSpent) {
                                this.unitTimeSpent = unitTimeSpent;
                            }

                            public String getUnitPercent() {
                                return unitPercent;
                            }

                            public void setUnitPercent(String unitPercent) {
                                this.unitPercent = unitPercent;
                            }

                            public Object getColorDueText() {
                                return colorDueText;
                            }

                            public void setColorDueText(Object colorDueText) {
                                this.colorDueText = colorDueText;
                            }

                            public String getColorScoreText() {
                                return colorScoreText;
                            }

                            public void setColorScoreText(String colorScoreText) {
                                this.colorScoreText = colorScoreText;
                            }

                            public Object getLateTime() {
                                return lateTime;
                            }

                            public void setLateTime(Object lateTime) {
                                this.lateTime = lateTime;
                            }

                            public String getSubmissionStatus() {
                                return submissionStatus;
                            }

                            public void setSubmissionStatus(String submissionStatus) {
                                this.submissionStatus = submissionStatus;
                            }

                            public Long getDuedats() {
                                return duedats;
                            }

                            public void setDuedats(Long duedats) {
                                this.duedats = duedats;
                            }

                            public String getTeacherGradeStatus() {
                                return teacherGradeStatus;
                            }

                            public void setTeacherGradeStatus(String teacherGradeStatus) {
                                this.teacherGradeStatus = teacherGradeStatus;
                            }

                        }

                    }


                }


            }

        }
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
        private List<Object> domainStudent = null;

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

        public List<Object> getDomainStudent() {
            return domainStudent;
        }

        public void setDomainStudent(List<Object> domainStudent) {
            this.domainStudent = domainStudent;
        }

    }

}
























