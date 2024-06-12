package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class workActivity extends AppCompatActivity {
    private Database dbHelper;
    private TaskAdapter taskAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        dbHelper = new Database(this);

        // Initialize the ListView and set the adapter
        ListView listView = findViewById(R.id.workTaskListView);
        List<Task> workTasks = dbHelper.getTasksByCategory("Work");
        taskAdapter = new TaskAdapter(this, new ArrayList<>(workTasks), dbHelper);
        listView.setAdapter(taskAdapter);

        // Set up bottom navigation click listeners
        ImageView navhome = findViewById(R.id.imageViewhomefam);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(workActivity.this, homeActivity.class));
            }
        });

        ImageView navaddtask = findViewById(R.id.imageView14);
        navaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(workActivity.this, AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(workActivity.this, MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(workActivity.this, settingsActivity.class));
            }
        });
    }
}
