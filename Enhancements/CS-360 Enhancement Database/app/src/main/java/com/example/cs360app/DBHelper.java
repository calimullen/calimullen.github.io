package com.example.cs360app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context, "WeightLoss.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create Table Users(username Text primary key, password Text)");
        db.execSQL("create Table Users(firstname Text, lastname Text, email Text, username Text, password Text, primary key (email, username))");
        db.execSQL("create Table Weights(id Integer primary key autoincrement, username Text, weight Text, date Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Users");
        db.execSQL("drop table if exists Weights");
        onCreate(db);
    }

    public boolean insertUser(String firstname, String lastname, String email,
                              String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("Users", null, contentValues);

        if(result==-1){
            return false;
        }
        return true;
    }
    public boolean getUser(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From Users Where username = ? And password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username, password});
        if(cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }
    public boolean getUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From Users Where username = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username});
        if(cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }
    public boolean getEmail(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From Users Where email = ?";
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if(cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }

    public Cursor getUserInfo(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From Users where username =?";
        return db.rawQuery(query, new String[] {username});
    }
    public boolean updateUserInfo(String oldUsername, String oldPassword, String firstName, String lastName, String email,
                                  String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstName);
        contentValues.put("lastname", lastName);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.update("Users", contentValues,
                "username=? AND password=?", new String[]{oldUsername, oldPassword});
        if (result == -1) {
            return false;
        }
        return true;
    }
    public boolean insertWeight(String username, String weight, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("weight", weight);
        contentValues.put("date", date);
        long result = db.insert("Weights", null, contentValues);

        if(result==-1){
            return false;
        }
        return true;
    }
    public boolean updateWeight(String username, String weight, String date,
                                String newWeight, String newDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", newWeight);
        contentValues.put("date", newDate);
        Cursor cursor = db.rawQuery("select * from Weights where username=? AND weight=? AND date=?", new String[]{username, weight, date});
        if (cursor.getCount() > 0) {
            long result = db.update("Weights", contentValues,
                    "username=? AND weight=? AND date=?", new String[] {username, weight, date});
            if (result == -1) {
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean deleteWeight(String weight, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Weights", "weight =? AND date =?", new String[] {weight, date});
        return result != -1;
    }
    public Cursor getWeights(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From Weights where username =?";
        return db.rawQuery(query, new String[] {username});
    }
}
