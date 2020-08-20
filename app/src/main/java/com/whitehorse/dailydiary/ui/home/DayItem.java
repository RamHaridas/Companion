package com.whitehorse.dailydiary.ui.home;

public class DayItem {
    int image;
    String hometv,hometv1;
    boolean isExpanded;
    String activities;

    public DayItem(int image, String hometv, String hometv1, boolean isExpanded, String activities) {
        this.image = image;
        this.hometv = hometv;
        this.hometv1 = hometv1;
        this.isExpanded = isExpanded;
        this.activities = activities;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public String getHometv() {
        return hometv;
    }
    public void setHometv(String hometv) {
        this.hometv = hometv;
    }
    public String getHometv1() {
        return hometv1;
    }
    public void setHometv1(String hometv1) {
        this.hometv1 = hometv1;
    }
    public boolean isExpanded() {
        return isExpanded;
    }
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    public String getActivities() {
        return activities;
    }
    public void setActivities(String activities) {
        this.activities = activities;
    }
}
