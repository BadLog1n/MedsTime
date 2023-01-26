package com.oneseed.medstime;

public class Meds {

    private String textInside;
    private Integer timesInside;
    private Integer countInside;
    private Integer idInside;

    public Meds(String text, Integer times, Integer count, Integer id){

        this.textInside=text;
        this.timesInside=times;
        this.countInside=count;
        this.idInside=id;
    }

    public String getTextInside() {
        return this.textInside;
    }

    public void setTextInside(String text) {
        this.textInside = text;
    }

    public Integer getTimesInside() {
        return this.timesInside;
    }

    public void setTimesInside(Integer times) {
        this.timesInside = times;
    }

    public Integer getCountInside() {
        return this.countInside;
    }

    public void setCountInside(Integer count) {
        this.countInside = count;
    }

    public Integer getIdInside() {
        return this.idInside;
    }

    public void setIdInside(Integer id) {
        this.idInside = id;
    }


}