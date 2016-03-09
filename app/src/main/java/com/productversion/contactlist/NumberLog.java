package com.productversion.contactlist;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Call;

import java.util.Date;

public class NumberLog
{

    String phone = "";
    String CallDate = "";
    String Calltype = "";
    String duration = "";
    boolean answered = false;

    public NumberLog(String phone_, String calldate_, String calltype_,String duration_) {
        this.phone = phone_;
        this.CallDate = calldate_;
        this.Calltype = calltype_;
        this.duration = duration_;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCallDate() {
        return CallDate;
    }

    public void setCallDate(String callDate) {
        CallDate = callDate;
    }

    public String getCalltype() {
        return Calltype;
    }

    public void setCalltype(String calltype) {
        Calltype = calltype;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}