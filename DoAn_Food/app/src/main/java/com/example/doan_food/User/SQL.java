package com.example.doan_food.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doan_food.MonAn.MonAn;

import java.util.ArrayList;

public class SQL extends SQLiteOpenHelper {
    public SQL(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_SV = "create table if not exists gioHang(id nvarchar(40),tenMonAn nvarchar(50) ,giaTien nvarchar(50),link_image text(50),SL nvarchar(50),chuThich nvarchar(50), primary key(id,tenMonAn))";
        db.execSQL(create_SV);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addHD(HoaDon hd,String id) {
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        ContentValues content = new ContentValues();
        content.put("id",id);
        content.put("tenMonAn",hd.getMonAn().getTenMonAn());
        content.put("giaTien",hd.getMonAn().getGiaTien());
        content.put("link_image",hd.getMonAn().getLink_image());
        content.put("chuThich",hd.getMonAn().getChuThich());
        content.put("SL",hd.getSL());
        flag = db.insert("gioHang",null,content) > 0;
        return flag;
    }

    public ArrayList<HoaDon> getHoaDon(String id){
        ArrayList<HoaDon> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * From gioHang";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            if(cursor.getString(0).equals(id)) {
                String tenMonAn = cursor.getString(1);
                String giaTien = cursor.getString(2);
                String link_image = cursor.getString(3);
                String chuThich = cursor.getString(5);
                String SL = cursor.getString(4);
                arrayList.add(new HoaDon(new MonAn(tenMonAn,giaTien,chuThich,link_image),SL));
            }
        }
        db.close();
        return arrayList;
    }

    public boolean updateHD(String id, String tenMA, int SL){
        boolean flag = false;
        //1
        SQLiteDatabase db = getReadableDatabase();
        //2
        ContentValues contentValues = new ContentValues();
        contentValues.put("SL", SL);
        //3
        flag = db.update("gioHang",contentValues,"tenMonAn='"+tenMA+"' and id='"+id+"'",null) > 0;
        //4
        db.close();
        return flag;
    }

    public boolean deleteHD(String id, String tenMA){
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        flag = db.delete("gioHang", "tenMonAn='"+tenMA+"' and id='"+id+"'", null) > 0;
        db.close();
        return flag;
    }

    public boolean deleteAllHD(String id){
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        flag = db.delete("gioHang", "id='"+id+"'", null) > 0;
        db.close();
        return flag;
    }
}

