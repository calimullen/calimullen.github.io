package com.example.cs360app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context, "WeightLoss.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users(username Text primary key, password Text)");
        db.execSQL("create Table Weights(id Integer primary key autoincrement, username Text, weight Text, date Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertUser(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
