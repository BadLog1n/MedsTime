package com.oneseed.medstime;

public class Meds {

    private final String textInside;
    private final Integer timesInside;
    private Integer countInside;

    public Meds(String text, Integer times, Integer count) {

        this.textInside = text;
        this.timesInside = times;
        this.countInside = count;
    }

    public String getTextInside() {
        return this.textInside;
    }
    public Integer getTimesInside() {
        return this.timesInside;
    }
    public Integer getCountInside() {
        return this.countInside;
    }
    public void setCountInside(Integer count) {
        this.countInside = count;
    }


}