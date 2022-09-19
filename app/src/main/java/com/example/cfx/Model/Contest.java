package com.example.cfx.Model;

public class Contest {
    String name,prevrating,newrating,rank;
    int rk,id;

    public Contest(String name, String prevrating, String newrating, String rank) {
        this.name = name;
        this.prevrating = prevrating;
        this.newrating = newrating;
        this.rank = rank;
    }
    public Contest(int id,String name, String prevrating, String newrating, String rank) {
        this.name = name;
        this.id=id;
        this.prevrating = prevrating;
        this.newrating = newrating;
        this.rank = rank;
    }
 public Contest(int id, int newrating) {
        this.id = id;
        this.rk = newrating;
    }
    public Contest(String name) {
      this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRk() {
        return rk;
    }

    public void setRk(int rk) {
        this.rk = rk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrevrating() {
        return prevrating;
    }

    public void setPrevrating(String prevrating) {
        this.prevrating = prevrating;
    }

    public String getNewrating() {
        return newrating;
    }

    public void setNewrating(String newrating) {
        this.newrating = newrating;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
