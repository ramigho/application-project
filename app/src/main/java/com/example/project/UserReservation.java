package com.example.project;


public class UserReservation {
    String time;
    String hall;
    String date;
    String infoline;
    String userID;

    public UserReservation(){ }

    public UserReservation(String userID, String hall, String date, String time, String infoline){
        this.time = time;
        this.hall = hall;
        this.infoline = infoline;
        this.date = date;
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public String getHall() {
        return hall;
    }

    public String getInfoline() {
        return infoline;
    }

    public String getUserID(){ return userID; }

    public String getDate(){ return date; }
}
