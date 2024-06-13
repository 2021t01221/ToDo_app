package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class settingsActivity extends AppCompatActivity {



    Switch switch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button save = findViewById(R.id.button4);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,settingsActivity.class));
            }
        });


        ImageView navhome = findViewById(R.id.imageView15);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,homeActivity.class));
            }
        });

        ImageView navddtask = findViewById(R.id.imageView14);
        navddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,settingsActivity.class));
            }
        });


        setContentView(R.layout.activity_settings);
        switch1 = findViewById(R.id.switch1);
    }

    public void onSwitchClick(View view) {
        if (switch1.isChecked()){
            Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "OFF",Toast.LENGTH_SHORT).show();
        }
    }
    }

