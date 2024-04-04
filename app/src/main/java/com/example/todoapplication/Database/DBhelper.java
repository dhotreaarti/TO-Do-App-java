package com.example.todoapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.todoapplication.model.TODoModel;
import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    private static final String Database_Name = "ToDo_Database";
    private static final String Table_Name = "ToDo_Table";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "TASK";
    private static final String Col_3 = "STATUS";

    public DBhelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT ,STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public void insertTask(TODoModel model) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        ContentValues values = new ContentValues();
        values.put(Col_2, model.getTask());
        values.put(Col_3, 0);
        db.insert(Table_Name, null, values);
        db.close(); // Close the database connection
    }

    public void updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        ContentValues values = new ContentValues();
        values.put(Col_2, task);
        db.update(Table_Name, values, "ID=?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    public void updateStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        ContentValues values = new ContentValues();
        values.put(Col_3, status);
        db.update(Table_Name, values, "ID=?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        db.delete(Table_Name, "ID=?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    public List<TODoModel> getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        Cursor cursor = null;
        List<TODoModel> modelList = new ArrayList<>();
        db.beginTransaction();
        try {
            cursor = db.query(Table_Name, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    TODoModel task = new TODoModel();
                    task.setId(cursor.getInt(cursor.getColumnIndex(Col_1)));
                    task.setTask(cursor.getString(cursor.getColumnIndex(Col_2)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndex(Col_3)));
                    modelList.add(task);
                }
            }
        } finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Close the database connection
        }
        return modelList;
    }
}
