package com.cs442.team2.smartbar;

/**
 * Created by SumedhaGupta on 10/27/16.
 */

public class WorkoutEntity {

    int wId;
    String date;
    String startTime;
    String endTime;
    int wUserId;
    String exercise;
    int exerReps;
    int exerSets;
    int barWeight;

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getwUserId() {
        return wUserId;
    }

    public void setwUserId(int wUserId) {
        this.wUserId = wUserId;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getExerReps() {
        return exerReps;
    }

    public void setExerReps(int exerReps) {
        this.exerReps = exerReps;
    }

    public int getExerSets() {
        return exerSets;
    }

    public void setExerSets(int exerSets) {
        this.exerSets = exerSets;
    }

    public int getBarWeight() {
        return barWeight;
    }

    public void setBarWeight(int weight) {
        this.barWeight = weight;
    }
}