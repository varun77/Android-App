package com.example.bhavik.onlinefoodordering;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Bhavik on 5/26/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ofo.db";

    private static final String TABLE_NAME = "users";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (id integer primary key not null ," +
    "name text not null , email text not null , password text not null);";


    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertContact(Contact c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query , null);

        int count = cursor.getCount();

        values.put(COLUMN_ID , count);
        values.put(COLUMN_NAME , c.getName());
        values.put(COLUMN_EMAIL , c.getEmail());
        values.put(COLUMN_PASSWORD , c.getPassword());


        db.insert(TABLE_NAME , null , values);
        db.close();
    }

    public String searchPass(String email)
    {
        String a,b;
        b = "not found";
        db = this.getReadableDatabase();
        String quary = "select email,password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(quary , null);

        if (cursor.moveToFirst())
        {
            do
            {
                a = cursor.getString(0);

                if (a.equals(email))
                {
                    b = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());

        }
        return  b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}