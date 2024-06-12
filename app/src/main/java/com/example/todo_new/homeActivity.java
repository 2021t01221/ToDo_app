package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {
    private Database db;
    private TextView textViewUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        ImageView navhome = findViewById(R.id.homehome);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,homeActivity.class));
            }
        });

        ImageView navddtask = findViewById(R.id.imageView8);
        navddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView11);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView12);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,settingsActivity.class));
            }
        });



        db = new Database(this);
        textViewUserName = findViewById(R.id.textViewUserName);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "User");

        String userName = db.getUserName(userEmail);
        textViewUserName.setText("Hello " + userName);

        // Your existing navigation and button click code
        setupNavigation();
    }

    private void setupNavigation() {
        CardView alltasks = findViewById(R.id.cardAllTasks);
        alltasks.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, AlltasksActivity.class)));

        CardView personal = findViewById(R.id.cardPersonal);
        personal.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, personalActivity.class)));

        CardView work = findViewById(R.id.cardWork);
        work.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, workActivity.class)));

        CardView family = findViewById(R.id.cardFamily);
        family.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, familyActivity.class)));

        CardView shopping = findViewById(R.id.cardShopping);
        shopping.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, shoppingActivity.class)));

        CardView other = findViewById(R.id.Other);
        other.setOnClickListener(view -> startActivity(new Intent(homeActivity.this, otherActivity.class)));
    }
}
