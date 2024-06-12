package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Locale;

public class AddtaskActivity extends AppCompatActivity {

    final Calendar mycalender = Calendar.getInstance();

    private EditText titleEditText, deadlineEditText, startTimeEditText, endTimeEditText,datePicker,timePicker;
    private Spinner remindSpinner, repeatSpinner, categorySpinner;
    private Button createTaskButton;
    private ImageView backArrowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        // Initialize views
        titleEditText = findViewById(R.id.editTextText5);
        deadlineEditText = findViewById(R.id.editTextText6);

        deadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddtaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myformat = "dd-MMM-YYYY";
                        SimpleDateFormat dateformat = new SimpleDateFormat(myformat, Locale.US);
                        deadlineEditText.setText(dateformat.format(mycalender.getTime()));


                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        startTimeEditText = findViewById(R.id.editTextText8);

        startTimeEditText.setOnClickListener(V->selecttime());


        endTimeEditText = findViewById(R.id.editTextText9);
        endTimeEditText.setOnClickListener(V->selecttime2());

        //startTimeEditText = findViewById(R.id.editTextText8);
        //endTimeEditText = findViewById(R.id.editTextText9);
        remindSpinner = findViewById(R.id.spinner);
        repeatSpinner = findViewById(R.id.spinnerRepeat);
        categorySpinner = findViewById(R.id.spinnerCategory);
        createTaskButton = findViewById(R.id.button);
        backArrowImageView = findViewById(R.id.imageViewBackArrow);

        // Set up click listeners for bottom navigation
        ImageView navhome = findViewById(R.id.imageViewAddtadkHome);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddtaskActivity.this,homeActivity.class));
            }
        });

        ImageView navaddtask = findViewById(R.id.imageView14);
        navaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddtaskActivity.this,AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddtaskActivity.this, MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddtaskActivity.this, settingsActivity.class));
            }
        });

        // Set up click listener for back arrow
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // Go back to the previous activity
            }
        });

        // Set up repeat spinner
        ArrayAdapter<CharSequence> repeatAdapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_options, android.R.layout.simple_spinner_item);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(repeatAdapter);

        // Set up remind spinner
        ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(this,
                R.array.remind_options, android.R.layout.simple_spinner_item);
        remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        remindSpinner.setAdapter(remindAdapter);

        // Set up category spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_options, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up click listener for create task button
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTaskToDatabase();
            }
        });
    }

    private void saveTaskToDatabase() {
        // Retrieve data from views
        String title = titleEditText.getText().toString();
        String deadline = deadlineEditText.getText().toString();
        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();
        String remind = remindSpinner.getSelectedItem().toString();
        String repeat = repeatSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        // Insert data into database
        Database dbHelper = new Database(getApplicationContext());
        dbHelper.insertTask(title, deadline, startTime, endTime, remind, repeat, category);

        // Go back to home activity after creating the task
        startActivity(new Intent(AddtaskActivity.this, homeActivity.class));
    }

    private void selecttime(){
        Calendar currenttime = Calendar.getInstance();
        int hour = currenttime.get(Calendar.HOUR_OF_DAY);
        int minute = currenttime.get(Calendar.MINUTE);
        TimePickerDialog timepickerdialog;
        timepickerdialog = new TimePickerDialog(AddtaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                currenttime.set(Calendar.HOUR_OF_DAY,hour);
                currenttime.set(Calendar.MINUTE,minute);

                String myformat = "HH-mm-ss";
                SimpleDateFormat dateformat2 = new SimpleDateFormat(myformat,Locale.US);
                startTimeEditText.setText(dateformat2.format(currenttime.getTime()));



            }
        },hour,minute,true);
        timepickerdialog.setTitle("Select time");
        timepickerdialog.show();;

    }

    private void selecttime2() {
        Calendar currenttime = Calendar.getInstance();
        int hour = currenttime.get(Calendar.HOUR_OF_DAY);
        int minute = currenttime.get(Calendar.MINUTE);
        TimePickerDialog timepickerdialog;
        timepickerdialog = new TimePickerDialog(AddtaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                currenttime.set(Calendar.HOUR_OF_DAY, hour);
                currenttime.set(Calendar.MINUTE, minute);

                String myformat2 = "HH-mm-ss";
                SimpleDateFormat dateformat2 = new SimpleDateFormat(myformat2, Locale.US);
                endTimeEditText.setText(dateformat2.format(currenttime.getTime()));


            }
        }, hour, minute, true);
        timepickerdialog.setTitle("Select time");
        timepickerdialog.show();
        ;


    }
}
