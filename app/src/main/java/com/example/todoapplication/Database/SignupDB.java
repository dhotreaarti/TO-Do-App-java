package com.example.todoapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SignupDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "signup_db";
    private static final int DATABASE_VERSION = 1;

    public SignupDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE signup(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS signup");

        onCreate(db);
    }

    public boolean registerUserHelper(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);

        long result = sqLiteDatabase.insert("signup", null, values);
        sqLiteDatabase.close();  // Close the database after use

        return result != -1;  // Return true if insertion was successful, false otherwise
    }

   /* public boolean checkUsername(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();  // Use getReadableDatabase for read-only operations

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM signup WHERE name=?", new String[]{name});
       // boolean exists = cursor.getCount() > 0;
        return cursor.getCount() > 0;


        //return exists;
    }*/

    public boolean login(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM signup WHERE email=? AND password=?", new String[]{email, password});
        boolean success = cursor.getCount() > 0;

        cursor.close();
        sqLiteDatabase.close();

        return success;
    }

}
