package com.example.cfx.Model;

public class Recent {
    String id,name,rank,index,mxpoints,ptobt,rejectedcnt,rating;

    public Recent( String name,String rating,  String index, String mxpoints, String ptobt, String rejectedcnt) {
        this.name = name;
        this.rating = rating;
        this.index = index;
        this.mxpoints = mxpoints;
        this.ptobt = ptobt;
        this.rejectedcnt = rejectedcnt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMxpoints() {
        return mxpoints;
    }

    public void setMxpoints(String mxpoints) {
        this.mxpoints = mxpoints;
    }

    public String getPtobt() {
        return ptobt;
    }

    public void setPtobt(String ptobt) {
        this.ptobt = ptobt;
    }

    public String getRejectedcnt() {
        return rejectedcnt;
    }

    public void setRejectedcnt(String rejectedcnt) {
        this.rejectedcnt = rejectedcnt;
    }
}
