package com.example.cfx.Model;

public class Upcoming {
    String name,type,duration,sttime,remainingtime;

    public Upcoming(String name, String type, String duration, String sttime, String remainingtime) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.sttime = sttime;
        this.remainingtime = remainingtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSttime() {
        return sttime;
    }

    public void setSttime(String sttime) {
        this.sttime = sttime;
    }

    public String getRemainingtime() {
        return remainingtime;
    }

    public void setRemainingtime(String remainingtime) {
        this.remainingtime = remainingtime;
    }
}
