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

public class shoppingActivity extends AppCompatActivity {
    private Database dbHelper;
    private TaskAdapter taskAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        dbHelper = new Database(this);

        // Initialize the ListView and set the adapter
        ListView listView = findViewById(R.id.shoppingTaskListView);
        List<Task> shoppingTasks = dbHelper.getTasksByCategory("Shopping");
        taskAdapter = new TaskAdapter(this, new ArrayList<>(shoppingTasks), dbHelper);
        listView.setAdapter(taskAdapter);

        // Set up bottom navigation click listeners
        ImageView navhome = findViewById(R.id.imageViewhomefam);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(shoppingActivity.this, homeActivity.class));
            }
        });

        ImageView navaddtask = findViewById(R.id.imageView14);
        navaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(shoppingActivity.this, AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(shoppingActivity.this, MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(shoppingActivity.this, settingsActivity.class));
            }
        });
    }
}
