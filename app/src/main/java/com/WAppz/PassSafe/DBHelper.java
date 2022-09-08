package com.WAppz.PassSafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.zetetic.database.sqlcipher.SQLiteDatabase;
import net.zetetic.database.sqlcipher.SQLiteOpenHelper;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;
    public static String dbName = "vaultdata.db";
    private File databasePath = vault_page.dbPath;

    public DBHelper (Context ct) {
        super(ct, dbName ,null,1);
        System.loadLibrary("sqlcipher");
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        db.execSQL("CREATE TABLE VaultDetails(Name TEXT Primary Key, Password TEXT, Type INTEGER, Sequence INTEGER, Date TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        db.execSQL("DROP TABLE if exists VaultDetails");
    }

    public Boolean insertdata(String name, String pass, Integer type, Integer seq, String d) {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
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
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
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
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        try {
            DB.execSQL("delete from VaultDetails");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getdata() {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        Cursor cursor = DB.rawQuery("Select * FROM VaultDetails",null);
        return cursor;
    }

    public Cursor sortdbASCName() {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name ASC",null);
        return cursor;
    }
    public Cursor sortdbDESCName() {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Name DESC",null);
        return cursor;
    }
    public Cursor sortdbASCSeq() {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence ASC",null);
        return cursor;
    }

    public Cursor sortdbDESCSeq() {
        System.loadLibrary("sqlcipher");
        SQLiteDatabase DB = SQLiteDatabase.openOrCreateDatabase(databasePath,signup.code1,null,null,null);
        Cursor cursor = DB.rawQuery("SELECT * FROM VaultDetails ORDER BY Sequence DESC",null);
        return cursor;
    }
}