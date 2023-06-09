package com.example.rhcs;

public class UserAppId {
    private String id, patientname, weight, age, gender, refDoc, refDocNum, clinicalDetail, history, anyOther, email, mobile, date;

    public UserAppId() {
    }

    public UserAppId(String id, String patientname, String weight, String age, String gender, String refDoc, String refDocNum, String clinicalDetail, String history, String anyOther, String email, String mobile, String date) {
        this.id = id;
        this.patientname = patientname;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.refDoc = refDoc;
        this.refDocNum = refDocNum;
        this.clinicalDetail = clinicalDetail;
        this.history = history;
        this.anyOther = anyOther;
        this.email = email;
        this.mobile = mobile;
        this.date = date;

    }


    public String getId() {
        return id;
    }

    public String getPatientname() {
        return patientname;
    }

    public String getWeight() {
        return weight;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public String getRefDocNum() {
        return refDocNum;
    }

    public String getClinicalDetail() {
        return clinicalDetail;
    }

    public String getHistory() {
        return history;
    }

    public String getAnyOther() {
        return anyOther;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDate() {
        return date;
    }
}
