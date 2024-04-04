package com.example.todoapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.todoapplication.model.NotesModel;
import com.example.todoapplication.model.TODoModel;
import java.util.ArrayList;
import java.util.List;

public class NotesDB extends SQLiteOpenHelper {

    private static final String Database_Name = "Notes_Database";
    private static final String Table_Name = "Note_Table";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "NOTE";
    private static final String Col_3 = "STATUS";

    public NotesDB(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ,NOTE TEXT ,STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public boolean insertNote(NotesModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2, model.getNote());
        values.put(Col_3, 0);
        long result = db.insert(Table_Name, null, values);
        db.close();
        return result != -1; // Return true if insertion was successful, false otherwise
    }


  /*  public void updateNote(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        ContentValues values = new ContentValues();
        values.put(Col_2, task);
        db.update(Table_Name, values, "ID=?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }*/

    public void updateNote(NotesModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2, note.getNote());
        values.put(Col_3, note.getStatus());
        db.update(Table_Name, values, Col_1 + "=?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        db.delete(Table_Name, "ID=?", new String[]{String.valueOf(id)});
        db.close(); // Close the database connection
    }

    public ArrayList<NotesModel> getAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase(); // Initialize SQLiteDatabase object
        Cursor cursor = null;
        ArrayList<NotesModel> modelList = new ArrayList<>();
        db.beginTransaction();
        try {
            cursor = db.query(Table_Name, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    NotesModel note = new NotesModel();
                    note.setId(cursor.getInt(cursor.getColumnIndex(Col_1)));
                    note.setNote(cursor.getString(cursor.getColumnIndex(Col_2)));
                    note.setStatus(cursor.getInt(cursor.getColumnIndex(Col_3)));
                    modelList.add(note);
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
