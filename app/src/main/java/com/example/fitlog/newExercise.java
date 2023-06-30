package com.example.fitlog;

public class newExercise {
    public String exercise;
    public String exercisename;

    public newExercise(String exercise, String exercisename) {
        this.exercisename = exercisename;
        this.exercise = exercise;
    }

    public newExercise() {
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExercisename() {
        return exercisename;
    }

    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }
}
