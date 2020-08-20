package com.whitehorse.dailydiary.data;

import java.util.List;

public class DailyData {

    String date;
    String day_status;
    List<String> stringList;
    boolean isExpanded;
    public DailyData(){}

    public String getDay_status() {
        return day_status;
    }

    public String getDate() {
        return date;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setDay_status(String day_status) {
        this.day_status = day_status;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
