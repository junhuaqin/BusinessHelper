package com.qfg.businesshelper.statistics.domain.model;

/**
 * Created by rbtq on 8/26/16.
 */
public class OrderStatistics {
    private long curMonth;
    private long curDay;

    public long getCurMonth() {
        return curMonth;
    }

    public void setCurMonth(long curMonth) {
        this.curMonth = curMonth;
    }

    public long getCurDay() {
        return curDay;
    }

    public void setCurDay(long curDay) {
        this.curDay = curDay;
    }
}
