package com.example.fitlog;

import java.time.LocalDate;

public class ScheduleUser {

    public String startDate;
    public String Wsplit;
    public int muscle; //1 = push, 2 = pull, 3 = legs

    public ScheduleUser(String Wsplit){
        LocalDate startDate = LocalDate.now();
        System.out.println(startDate.toString());
        this.startDate = startDate.toString();
        this.Wsplit = Wsplit;
        this.muscle = 1;
        //System.out.println(Wsplit + "SENDER");

    }

    public ScheduleUser(){

    }
}
