package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MyprofileActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextOldPassword, editTextNewPassword, editTextConfirmPassword;
    ImageView editbtn1, editbtn2;
    Button buttonSaveChanges;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new Database(getApplicationContext());

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        editbtn1 = findViewById(R.id.imageViewpen1);
        editbtn2 = findViewById(R.id.imageViewpen2);

        // Load the user's data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        editTextEmail.setText(email);
        editTextName.setText(db.getUserName(email));

        editbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enable editing for name and email
                editTextName.setEnabled(true);
                editTextEmail.setEnabled(true);
            }
        });

        editbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enable editing for password fields
                editTextOldPassword.setEnabled(true);
                editTextNewPassword.setEnabled(true);
                editTextConfirmPassword.setEnabled(true);
            }
        });

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editTextName.getText().toString();
                String newEmail = editTextEmail.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (!newPassword.isEmpty() || !confirmPassword.isEmpty() || !oldPassword.isEmpty()) {
                    if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(MyprofileActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Database.isValid(newPassword)) {
                        Toast.makeText(MyprofileActivity.this, "Password must be exactly 8 characters, including letters, digits, and symbols", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (db.login(email, oldPassword) != 1) {
                        Toast.makeText(MyprofileActivity.this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db.updatePassword(email, newPassword);
                }

                db.updateUserProfile(email, newName, newEmail);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", newEmail);
                editor.apply();

                Toast.makeText(MyprofileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyprofileActivity.this, homeActivity.class));
                finish();
            }
        });

        setupNavigation();
    }

    private void setupNavigation() {
        ImageView navhome = findViewById(R.id.imageView15);
        navhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyprofileActivity.this, homeActivity.class));
            }
        });

        ImageView navddtask = findViewById(R.id.imageView14);
        navddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyprofileActivity.this, AddtaskActivity.class));
            }
        });

        ImageView navprofile = findViewById(R.id.imageView16);
        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyprofileActivity.this, MyprofileActivity.class));
            }
        });

        ImageView navsettings = findViewById(R.id.imageView17);
        navsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyprofileActivity.this, settingsActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_device_info:
                Toast.makeText(this, "Device Info", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyprofileActivity.this, dev_infoActivity.class));
                break;
            case R.id.action_logout:
                showLogoutConfirmation();
                break;
            case android.R.id.home: // Handle the back button in toolbar
                onBackPressed();
                break;
        }
        return true;
    }

    private void showLogoutConfirmation() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MyprofileActivity.this);
        bottomSheetDialog.setContentView(R.layout.dialog_logout_confirmation);

        bottomSheetDialog.findViewById(R.id.button_yes).setOnClickListener(v -> {
            // Handle logout logic
            bottomSheetDialog.dismiss();
            logout();
        });

        bottomSheetDialog.findViewById(R.id.button_no).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MyprofileActivity.this, loginActivity.class));
        finish();
    }
}
