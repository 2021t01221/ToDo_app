package com.example.todo_new;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlltasksActivity extends AppCompatActivity {

    ListView listView;
    Database dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alltasks);


        ImageView navhome = findViewById(R.id.imageView15);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlltasksActivity.this,homeActivity.class));
            }
        });

        ImageView navddtask = findViewById(R.id.imageView14);
        navddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlltasksActivity.this,AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlltasksActivity.this,MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlltasksActivity.this,settingsActivity.class));
            }
        });


        listView = findViewById(R.id.listViewTasks);
        dbHelper = new Database(getApplicationContext());

        // Retrieve tasks from the database
        ArrayList<Task> tasksList = getTasks();

        // Display tasks in ListView
        TaskAdapter adapter = new TaskAdapter(this, tasksList, dbHelper);
        listView.setAdapter(adapter);
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        // Query the database to retrieve tasks
        Cursor cursor = dbHelper.getReadableDatabase().query(
                "tasks", // Table name
                new String[]{"id", "title", "deadline", "start_time", "end_time", "remind", "repeat", "category"}, // Columns to retrieve
                null, // Selection (WHERE clause)
                null, // Selection arguments
                null, // Group by
                null, // Having
                null // Order by
        );

        // Check if the cursor is not null and has at least one row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve task details
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex("start_time"));
                @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex("end_time"));
                @SuppressLint("Range") String remind = cursor.getString(cursor.getColumnIndex("remind"));
                @SuppressLint("Range") String repeat = cursor.getString(cursor.getColumnIndex("repeat"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                // Add task to the list
                Task task = new Task(id, title, deadline, startTime, endTime, remind, repeat, category);
                tasksList.add(task);
            } while (cursor.moveToNext()); // Move to the next row
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        return tasksList;
    }
}
