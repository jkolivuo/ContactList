package com.productversion.contactlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Contacts  {

    int Id = 0;
    String phone = "";
    String last_contact_date = "";
    Date last_contact = new Date();
    boolean offer_left = false;
    int number_of_calls = 0;
    String type;
    String duration;
    String type_text = "";
    ArrayList<NumberLog> listOfCalls;




    public Contacts() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getNumber_of_calls() {
        return number_of_calls;
    }

    public void setNumber_of_calls(int number_of_calls) {
        this.number_of_calls = number_of_calls;
    }

    public String getLast_contact_date() {
        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        last_contact_date = sf.format(last_contact);
        return last_contact_date;
    }

    public void setLast_contact_date(String last_contact_date) {
        this.last_contact_date = last_contact_date;
    }

    public Date getLast_contact() {
        return last_contact;
    }

    public void setLast_contact(Date last_contact) {
        this.last_contact = last_contact;
    }

    public boolean isOffer_left() {
        return offer_left;
    }

    public void setOffer_left(boolean offer_left) {
        this.offer_left = offer_left;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public ArrayList<NumberLog> getListOfCalls() {
        return listOfCalls;
    }

    public void setListOfCalls(ArrayList<NumberLog> listOfCalls) {
        this.listOfCalls = listOfCalls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts = (Contacts) o;

        return getPhone().equals(contacts.getPhone());

    }

    @Override
    public int hashCode() {
        return getPhone().hashCode();
    }

}

