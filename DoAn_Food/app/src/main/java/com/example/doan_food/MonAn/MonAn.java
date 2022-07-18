package com.example.doan_food.MonAn;

import java.io.Serializable;

public class MonAn implements Serializable {
    String tenMonAn;
    String giaTien;
    String chuThich;
    String link_image;

    public MonAn(String tenMonAn, String giaTien, String chuThich ,String link_image) {
        this.tenMonAn = tenMonAn;
        this.giaTien = giaTien;
        this.chuThich = chuThich;
        this.link_image = link_image;
    }

    public MonAn() {
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    public String getLink_image() {
        return link_image;
    }

    public void setLink_image(String link_image) {
        this.link_image = link_image;
    }
}
