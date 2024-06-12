package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;
import java.util.ArrayList;

public class otherActivity extends AppCompatActivity {
    private ListView otherTaskListView;
    private TaskAdapter taskAdapter;
    private Database dbHelper;
    private List<Task> otherTasks;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        // Initialize the database helper
        dbHelper = new Database(this);

        // Initialize views
        otherTaskListView = findViewById(R.id.otherTaskListView);

        // Fetch fother tasks from the database
        otherTasks = dbHelper.getTasksByCategory("Other");

        // Set up the task adapter
        taskAdapter = new TaskAdapter(this, new ArrayList<>(otherTasks), dbHelper);
        otherTaskListView.setAdapter(taskAdapter);

        // Set up bottom navigation click listeners
        ImageView navhome = findViewById(R.id.imageViewhomefam);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otherActivity.this, homeActivity.class));
            }
        });

        ImageView navddtask = findViewById(R.id.imageView14);
        navddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otherActivity.this, AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otherActivity.this, MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otherActivity.this, settingsActivity.class));
            }
        });
    }
}
