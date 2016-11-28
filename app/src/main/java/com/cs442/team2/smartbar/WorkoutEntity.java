package com.cs442.team2.smartbar;

import java.io.Serializable;

/**
 * Created by SumedhaGupta on 10/27/16.
 */

public class WorkoutEntity implements Serializable{

    String wId;
    String date;
    String startTime;
    String endTime;
    int wUserId;
    String exercise;
    String exerReps;
    String exerSets;
    String barWeight;
    String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
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

    public String getExerReps() {
        return exerReps;
    }

    public void setExerReps(String exerReps) {
        this.exerReps = exerReps;
    }

    public String getExerSets() {
        return exerSets;
    }

    public void setExerSets(String exerSets) {
        this.exerSets = exerSets;
    }

    public String getBarWeight() {
        return barWeight;
    }

    public void setBarWeight(String weight) {
        this.barWeight = weight;
    }
}