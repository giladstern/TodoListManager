package com.example.gilad.todolistmanager;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Gilad on 3/26/2017.
 */
public class DataEntry implements Serializable, Comparable <DataEntry>{

    private String task;
    private int day;
    private int month;
    private int year;

    public DataEntry() {
        this("", 1,1,1);
    }

    public DataEntry(String s, int d, int m, int y){
        task = s;
        day = d;
        month = m;
        year = y;
    }

    public String getTask() {
        return task;
    }

    public int getDay(){
        return day;
    }

    public int getMonth() {
        return  month;
    }

    public int getYear() {
        return year;
    }

    public String dateString() {
        return String.format("%d/%d/%d", day, month + 1, year);
    }

    @Override
    public int compareTo(DataEntry o) {
        if (year < o.getYear()) {
            return -1;
        } else if (year > o.getYear()){
            return 1;
        } else if (month < o.getMonth()){
            return -1;
        } else if (month > o.getMonth()) {
            return 1;
        } else if (day < o.getDay()) {
            return -1;
        } else if (day > o.getDay()) {
            return 1;
        } else {
            return 0;
        }
    }
}
