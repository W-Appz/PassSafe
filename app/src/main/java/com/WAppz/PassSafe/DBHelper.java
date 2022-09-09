package com.WAppz.PassSafe;

import android.database.Cursor;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

public class DBHelper {

    public void insertdata(SQLiteDatabase DB, String name, String pass, Integer type, Integer seq, String d) {
        DB.execSQL("INSERT INTO VaultDetails VALUES (?,?,?,?,?)", new Object[]{name,pass,type,seq,d});
    }

    public void deletedata(SQLiteDatabase DB, String name) {
        DB.execSQL("DELETE FROM VaultDetails WHERE Name = ?", new String[]{name});
    }

    public void deleteAll(SQLiteDatabase DB) {
        DB.execSQL("delete from VaultDetails");
    }

    public Cursor getdata(SQLiteDatabase DB) {
        return DB.rawQuery("Select * FROM VaultDetails",null);
    }

    public Cursor sortdbASCName(SQLiteDatabase DB) {
        return DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name ASC",null);
    }

    public Cursor sortdbDESCName(SQLiteDatabase DB) {
        return DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name DESC",null);
    }
    public Cursor sortdbASCSeq(SQLiteDatabase DB) {
        return DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence ASC",null);
    }

    public Cursor sortdbDESCSeq(SQLiteDatabase DB) {
        return DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence DESC",null);
    }
}