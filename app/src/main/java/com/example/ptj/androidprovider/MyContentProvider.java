package com.example.ptj.androidprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       SQLiteDatabase db=helper.getWritableDatabase();
        int rowcount=db.delete("Students",
                selection,
                selectionArgs);
        return rowcount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=helper.getWritableDatabase();
        long id=db.insert("Students",null,values);
        // content://db1.abc.com
        // content://db1.abc.com/2
        Uri result=ContentUris.withAppendedId(uri, id);
        return result;

    }

    MyDBHelper helper;
    @Override
    public boolean onCreate() {
        helper=new MyDBHelper(getContext(),
                "Students.db",null,3);
        //create file
        //connect web server
        return true;
    }
    //project => {"studentCode","address"}
    //selection => "studentCode = ? and address=?"
    //


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //query from SQLite
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query("Students",projection,selection,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=helper.getWritableDatabase();
        int rowcount=db.update("Students",
                values,
                selection,
                selectionArgs);
        return rowcount;
    }

    private class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //run when app installed
            //sample data (remove when on production)
            db.execSQL("CREATE TABLE Students("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "studentCode TEXT,"+
                    "studentName TEXT,"+
                    "address TEXT,"+
                    "age INTEGER"+
                    ")");
            db.execSQL("INSERT INTO Students("+
                    "studentCode,studentName,address,age) "+
                    "VALUES('5001','Peter','1/235 Bangkok','10');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS Students;");
            onCreate(db);

        }
    }
}
