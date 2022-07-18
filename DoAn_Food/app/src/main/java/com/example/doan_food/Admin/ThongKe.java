package com.example.doan_food.Admin;

public class ThongKe {
    String Date;
    int tongTien;

    public ThongKe() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public ThongKe(String date, int tongTien) {
        Date = date;
        this.tongTien = tongTien;
    }
}
