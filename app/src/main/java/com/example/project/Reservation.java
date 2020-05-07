package com.example.project;

import java.util.ArrayList;

public class Reservation {
    private String status;
    private String hall;
    private int time;
    private String date;
    ArrayList<String> timeList;

    public Reservation(){}

    public Reservation(String text1, String text2, int timeId, String dateStr){
        timeList = new ArrayList<>();
        initialize();

        status = text1;
        hall = text2;
        time = timeId;
        date = dateStr;
    }

    public String getStatus(){
        return status;
    }

    public String getHall(){
        return hall;
    }

    public String getTime(){
        return timeList.get(time);
    }

    public String getDate(){ return date; }

    public void initialize(){
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");
        timeList.add("18:00");
        timeList.add("19:00");
        timeList.add("20:00");
    }
}