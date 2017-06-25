package com.example.aayushsrivastava.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by Aayush Srivastava on 06-06-2017.
 */

public class item_database extends SQLiteOpenHelper {

    SQLiteDatabase data;
    public item_database(Context context) {
        super(context, "database" , null, 10);
    }


    private static final String DATABASE_CREATE_TABLE_ITEMS = "CREATE TABLE IF NOT EXISTS items" +
            "(id integer autoincrement primary key, name varchar, price integer,quantity integer);";

    private static final String DATABASE_ALTER_ITEMS = "ALTER TABLE items ADD COLUMN alternate_name"+(id)+"" +
            " varchar DEFAULT null";


    //private static final String DATABASE_INSERT_INTO_ITEMS = "INSERT INTO items (alternate_name"+id+") VALUES('"+value1.get(id)+"')";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "items");
        onCreate(db);
    }

/*
    public String[] getColumnNames11(){
        data = getReadableDatabase();
        Cursor dbcirsor = data.query("items",null,null,null,null,null,null);
        String[] columnNames = dbcirsor.getColumnNames();
        return columnNames;
    }
    */
}