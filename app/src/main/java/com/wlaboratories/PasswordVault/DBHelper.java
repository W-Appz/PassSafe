package com.wlaboratories.PasswordVault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper (Context ct) {
        super(ct, "vaultdata.db" ,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE VaultDetails (Name TEXT Primary Key, Password TEXT, Type INTEGER, Sequence INTEGER, Date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE if exists VaultDetails");
    }

    public Boolean insertdata(String name, String pass, Integer type, Integer seq, String d) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put("Name",name);
        cV.put("Password",pass);
        cV.put("Type",type);
        cV.put("Sequence",seq);
        cV.put("Date",d);
        long result = DB.insert("VaultDetails",null,cV);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * FROM VaultDetails WHERE name = ?",new String[]{name});
        if (cursor.getCount()>0){
            long result = DB.delete("VaultDetails","Name=?",new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } return false;
    }
    public boolean deleteAll() {
        SQLiteDatabase DB = this.getWritableDatabase();
        try {
            DB.execSQL("delete from VaultDetails");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * FROM VaultDetails",null);
        return cursor;
    }

    public Cursor sortdbASCName() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name ASC",null);
        return cursor;
    }
    public Cursor sortdbDESCName() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name DESC",null);
        return cursor;
    }
    public Cursor sortdbASCSeq() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence ASC",null);
        return cursor;
    }

    public Cursor sortdbDESCSeq() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence DESC",null);
        return cursor;
    }


}
