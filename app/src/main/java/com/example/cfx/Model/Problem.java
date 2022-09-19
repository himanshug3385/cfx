package com.example.cfx.Model;

public class Problem {
    String name, index, tag, rating, ci;


    public Problem(String name, String index, String tag, String rating, String ci) {
        this.name = name;
        this.index = index;
        this.tag = tag;
        this.rating = rating;
        this.ci = ci;
    }

    public Problem(String name, String rating) {
        this.name = name;
        this.rating = rating;

    }


    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
