package com.example.doan_food.User;

import com.example.doan_food.MonAn.MonAn;

import java.io.Serializable;

public class HoaDon implements Serializable {
    MonAn monAn;
    String SL;
    public HoaDon(){}

    @Override
    public String toString() {
        return "HoaDon{" +
                "monAn=" + monAn +
                ", SL='" + SL + '\'' +
                '}';
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public HoaDon(MonAn monAn, String SL) {
        this.monAn = monAn;
        this.SL = SL;
    }
}
