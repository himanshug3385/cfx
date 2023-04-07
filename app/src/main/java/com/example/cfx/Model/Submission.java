package com.example.cfx.Model;

public class Submission {
    private String questionname,contestid,idx;
    private boolean verdict;
    private String dat;

    public String getContestid() {
        return contestid;
    }

    public void setContestid(String contestid) {
        this.contestid = contestid;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public boolean isVerdict() {
        return verdict;
    }

    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }

    private Submission(){}
    public Submission(String questionname,boolean verdict,String dt,String contestid ,String idx) {
        this.questionname = questionname;
        this.verdict=verdict;
        this.dat=dt;
        this.contestid=contestid;
        this.idx=idx;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }
}
