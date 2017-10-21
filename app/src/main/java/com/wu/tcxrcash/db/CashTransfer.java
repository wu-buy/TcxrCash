package com.wu.tcxrcash.db;

public class CashTransfer {

    private Integer day;
    private Integer cashDay;
    private Integer cashSum;
    private Integer damiDay;
    private Integer damiSum;
    private Integer xiaomiRest;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getCashDay() {
        return cashDay;
    }

    public void setCashDay(Integer cashDay) {
        this.cashDay = cashDay;
    }

    public Integer getCashSum() {
        return cashSum;
    }

    public void setCashSum(Integer cashSum) {
        this.cashSum = cashSum;
    }

    public Integer getDamiDay() {
        return damiDay;
    }

    public void setDamiDay(Integer damiDay) {
        this.damiDay = damiDay;
    }

    public Integer getDamiSum() {
        return damiSum;
    }

    public void setDamiSum(Integer damiSum) {
        this.damiSum = damiSum;
    }

    public Integer getXiaomiRest() {
        return xiaomiRest;
    }

    public void setXiaomiRest(Integer xiaomiRest) {
        this.xiaomiRest = xiaomiRest;
    }
}
