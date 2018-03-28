/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetDbHelper;
import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetsEntry;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    PetDbHelper mDbHelper = new PetDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //PetDbHelper mDbHelper = new PetDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        String[] projection ={ PetsEntry._ID,PetsEntry.COLUMN_PET_NAME,PetsEntry.COLUMN_PET_BREED,PetsEntry.COLUMN_PET_GENDER,PetsEntry.COLUMN_PET_WEIGHT};

        Cursor cursor  =db.query(PetsEntry.TABLE_NAME,projection,null,null,null,null,null );
        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(PetsEntry._ID + " - " +
                    PetsEntry.COLUMN_PET_NAME + "-"+PetsEntry.COLUMN_PET_BREED+"-"+PetsEntry.COLUMN_PET_WEIGHT+"-"+PetsEntry.COLUMN_PET_GENDER+"\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED);
            int weightColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_WEIGHT);
            int genderColumIndex =cursor.getColumnIndex(PetsEntry.COLUMN_PET_GENDER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                int currentGender = cursor.getInt(genderColumIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName+"-"+currentBreed+"-"+currentWeight+"-"+currentGender));
            }
        }
        finally{
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }

    private void insertPet(){
       // PetDbHelper mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(PetsEntry._ID, (int) null);
        values.put(PetsEntry.COLUMN_PET_NAME,"toto");
        values.put(PetsEntry.COLUMN_PET_BREED,"Terrier");
        values.put(PetsEntry.COLUMN_PET_GENDER,PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_PET_WEIGHT,7);
        long newRowId = db.insert(PetsEntry.TABLE_NAME, null, values);

    }
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
