package com.company;

public class Guest {

    // this is just a template, to offload the code

    private String name;
    private String attendance;
    private String email;
    private String password;
    int guestNum= 0;

    public Guest(String name, String attendance, String email, String password) {
        this.name = name;
        this.attendance = attendance;
        this.email= email;
        this.password=password;
        this.guestNum= guestNum;

    }

    public String getName() {
        return name;
    }

    public String getAttendance() {
        return attendance;
    }

    public String getEmail() {
        return email;
    }

    public int getGuestNum() {
        return guestNum;
    }


    @Override
    public String toString() {
        return "Guest{" +
                "name= " + name +
                " }";
    }
}
