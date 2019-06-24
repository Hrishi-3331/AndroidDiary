package com.hrishi_3331.devstudio3331.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Events";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "Title";
    private static final String COL_3 = "Filename";
    private static final String COL_4 = "Date";
    private static final String COL_5 = "Time";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean AddData(String Title, String Filename, String date, String time){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, Title);
        values.put(COL_3, Filename);
        values.put(COL_4, date);
        values.put(COL_5, time);

        long res = database.insert(TABLE_NAME, null, values);

        if (res == -1){
            return false;
        }
        else return true;
    }

    public Cursor getEvents(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return database.rawQuery(query, null);
    }

    public void deleteEvent(String filename, String date){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, "Filename=?", new String[]{filename});
    }
}
