package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetsEntry;


/**
 * Created by akhilamadari on 1/25/18.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public PetDbHelper(Context context) {

        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME ="pets.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PetsEntry.TABLE_NAME + " ("
                               + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                                + PetsEntry.COLUMN_PET_BREED + " TEXT, "
                                + PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                        + PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
        Log.v("habit", "created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
