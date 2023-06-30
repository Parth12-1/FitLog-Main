package com.example.fitlog;

public class UserCreate {

    public String email;
    public String age;
    public String height;
    public String Weight;
    public String Bfat;
    public int Endgoal;
    public ScheduleUser scheduleUser;

    public UserCreate() {
        // Default constructor required for calls to DataSnapshot.getValue(UserCreate.class)
    }

    public UserCreate(String username, String email, String age, String height, String Weight, String Bfat, int Endgoal, boolean Wsplit) {


        this.email = email;
        this.age=age;
        this.height = height;
        this.Weight = Weight;
        this.Bfat = Bfat;
        this.Endgoal = Endgoal; // 0 = lose 1 = maintain 2 = gain
        this.scheduleUser = new ScheduleUser(String.valueOf(Wsplit));
        //System.out.println(Wsplit);
    }

}
