package com.khataab.changeenglishlanguagecenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_Helper extends SQLiteOpenHelper {


    public Database_Helper(@Nullable Context context) {
        super(context, "Audio_Database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Audio_Table (BookName TEXT,DownloadUrl TEXT,Type TEXT, Unit TEXT,FileName Text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table  IF EXISTS Audio_Table");

    }

    public boolean insertData(String bookName, String downloadUrl, String type, String unit, String fileName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Check if the same record already exists
        if (isRecordExists(sqLiteDatabase, bookName, downloadUrl, type, unit, fileName)) {
            // The record already exists, so return false
            return true;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("BookName", bookName);
        contentValues.put("DownloadUrl", downloadUrl);
        contentValues.put("Type", type);
        contentValues.put("Unit", unit);
        contentValues.put("FileName", fileName);

        long result = sqLiteDatabase.insert("Audio_Table", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    private boolean isRecordExists(SQLiteDatabase db, String bookName, String downloadUrl, String type, String unit, String fileName) {
        String query = "SELECT COUNT(*) FROM Audio_Table WHERE " +
                "BookName = ? AND DownloadUrl = ? AND Type = ? AND Unit = ? AND FileName = ?";
        String[] args = {bookName, downloadUrl, type, unit, fileName};
        Cursor cursor = db.rawQuery(query, args);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }

        return false;
    }


    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("Select * from Audio_Table",null);
        return res;
    }
}
