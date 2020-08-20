package com.whitehorse.dailydiary.data;

import com.google.gson.annotations.SerializedName;

public class WeeklyMoodStatus {

    @SerializedName("great")
    private int great;
    @SerializedName("normal")
    private int normal;
    @SerializedName("sad")
    private int sad;
    @SerializedName("sleep early")
    private int sleep_early;
    @SerializedName("sleep good")
    private int sleep_good;
    @SerializedName("sleep medium")
    private int sleep_medium;
    @SerializedName("sleep bad")
    private int sleep_bad;
    @SerializedName("family")
    private int family;
    @SerializedName("friend")
    private int friend;
    @SerializedName("eat healty")
    private int eat_healty;
    @SerializedName("eat_homemade")
    private int eat_homemade;
    @SerializedName("eat_fastfood")
    private int eat_fastfood;
    @SerializedName("eat soda")
    private int eat_soda;
    @SerializedName("eat sweets")
    private int eat_sweets;
    @SerializedName("read")
    private int read;
    @SerializedName("gaming")
    private int gaming;
    @SerializedName("movie")
    private int movie;
    @SerializedName("party")
    private int party;
    @SerializedName("workout")
    private int workout;

    public WeeklyMoodStatus(){}



    public int getGreat() {
        return great;
    }

    public void setGreat(int great) {
        this.great = great;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public int getSleep_early() {
        return sleep_early;
    }

    public void setSleep_early(int sleep_early) {
        this.sleep_early = sleep_early;
    }

    public int getSleep_good() {
        return sleep_good;
    }

    public void setSleep_good(int sleep_good) {
        this.sleep_good = sleep_good;
    }

    public int getSleep_medium() {
        return sleep_medium;
    }

    public void setSleep_medium(int sleep_medium) {
        this.sleep_medium = sleep_medium;
    }

    public int getSleep_bad() {
        return sleep_bad;
    }

    public void setSleep_bad(int sleep_bad) {
        this.sleep_bad = sleep_bad;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public int getEat_healty() {
        return eat_healty;
    }

    public void setEat_healty(int eat_healty) {
        this.eat_healty = eat_healty;
    }

    public int getEat_homemade() {
        return eat_homemade;
    }

    public void setEat_homemade(int eat_homemade) {
        this.eat_homemade = eat_homemade;
    }

    public int getEat_fastfood() {
        return eat_fastfood;
    }

    public void setEat_fastfood(int eat_fastfood) {
        this.eat_fastfood = eat_fastfood;
    }

    public int getEat_soda() {
        return eat_soda;
    }

    public void setEat_soda(int eat_soda) {
        this.eat_soda = eat_soda;
    }

    public int getEat_sweets() {
        return eat_sweets;
    }

    public void setEat_sweets(int eat_sweets) {
        this.eat_sweets = eat_sweets;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getGaming() {
        return gaming;
    }

    public void setGaming(int gaming) {
        this.gaming = gaming;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public int getWorkout() {
        return workout;
    }

    public void setWorkout(int workout) {
        this.workout = workout;
    }
}
