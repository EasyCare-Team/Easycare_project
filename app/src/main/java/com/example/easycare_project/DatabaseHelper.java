package com.example.easycare_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME = "register.db";
    public static  final String TABLE_NAME = "registeruser";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "email";
    public static  final String COL_3 = "username";
    public static  final String COL_4 = "password";
    //public static  final String col4 = "register.db";

    public static  final String  COLUMN_ITEM ="image";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("Create table registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT,  username VARCHAR NOT NULL UNIQUE,  email VARCHAR UNIQUE, password TEXT UNIQUE, dob VARCHAR, gender CHAR, weight NUMBER, height NUMBER, image BLOB DEFAULT X'00', UNIQUE(username, email))");
        db.execSQL("Create table measurement (ID INTEGER PRIMARY KEY AUTOINCREMENT, temprature VARCHAR, bp VARCHAR, heartrate VARCHAR, bmi VARCHAR, date TEXT, uname VARCHAR, FOREIGN KEY (uname)\n" +
                "REFERENCES registeruser (username))");
       // db.execSQL("Create table user_profile (ID INTEGER PRIMARY KEY,  username VARCHAR NOT NULL,  email VARCHAR, password TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public boolean CheckIsInDBorNot(String uname) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE  username ='"+uname+"'" ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE  username ='"+uname+"'", null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean isValueExist(String user, String email){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + " = ?" + " and " + COL_2 + "=?";
        String[] whereArgs = {user, email};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();

        return count >= 1;
    }

    public long addUser(String email, String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res=0;
        if(!isValueExist(user, email)) {
            res = db.insertWithOnConflict("registeruser", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            System.out.print(res);
            Log.d("kkkkkkkkkk", "" + res);
            db.close();
        }
            return res;

    }

    public int updateProfile(String user, String dob, String gender, String height, String weight, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] whereArgs ={user};
        String where = "username=?";
        values.put("username", user);
        values.put("dob", dob);
        values.put("gender", gender);

        values.put("height", height);

        values.put("weight", weight);
        values.put("image", image);

        return db.update("registerUser", values, where, whereArgs);
    }

    public Cursor fetchProfile(String uname){
        //SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE  username ='"+uname+"'", null);
        if (cursor.getCount() == 0) {
            cursor.close();

        }
        Log.d("Cursor", DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }



    public Bitmap getImage(String uname) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bmp = null;
        byte[] blob;
        Cursor c = db.rawQuery("SELECT image FROM " + TABLE_NAME + " WHERE  username ='" + uname + "'", null);
        if (c.moveToNext())
        {
            blob = c.getBlob(0);
            c.close();
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        return null;

    }


    public long addMeasurement(String user, String value , String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datee = new Date();
        contentValues.put("user", user);
        contentValues.put("date", dateFormat.format(datee));
        if (type == "TEMP"){
            contentValues.put("temp", value);
        }
        else if(type == "BP"){
            contentValues.put("bp", value);
        }
        else if(type == "HEART"){
            contentValues.put("heartrate", value);
        }
        else if(type == "BMI"){
            contentValues.put("bmi", value);
        }
        long res = db.insert ("measurement", null, contentValues);
        System.out.print(res);
        Log.d("kkkkkkkkkk",""+ res);
        db.close();
        return res;
    }
    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return true;
        else
            return false;


    }
    }
  