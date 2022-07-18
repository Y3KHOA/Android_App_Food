package com.example.doan_food.User;

import java.util.ArrayList;

public class DatHang {
    ArrayList<HoaDon> hd;
    String ngayDat;
    String ngDat;
    String tongTien;
    String thanhToan;
    String DiaChi;

    public DatHang(ArrayList<HoaDon> hd, String ngayDat, String ngDat, String tongTien, String thanhToan, String diaChi) {
        this.hd = hd;
        this.ngayDat = ngayDat;
        this.ngDat = ngDat;
        this.tongTien = tongTien;
        this.thanhToan = thanhToan;
        DiaChi = diaChi;
    }

    public ArrayList<HoaDon> getHd() {
        return hd;
    }

    public void setHd(ArrayList<HoaDon> hd) {
        this.hd = hd;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgDat() {
        return ngDat;
    }

    public void setNgDat(String ngDat) {
        this.ngDat = ngDat;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public DatHang() {
    }
}
