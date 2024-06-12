package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btn_login, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextLoginEmail);
        password = findViewById(R.id.editTextLoginPassword);
        btn_login = findViewById(R.id.buttonLogLogin);
        btn_signup = findViewById(R.id.buttonLogSignup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_mail = email.getText().toString();
                String pwd = password.getText().toString();
                Database db = new Database(getApplicationContext());

                if (e_mail.length() == 0 || pwd.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("LoginActivity", "Email: " + e_mail + ", Password: " + pwd);
                    if (db.login(e_mail, pwd) == 1) {
                        Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", e_mail);
                        editor.apply();
                        startActivity(new Intent(loginActivity.this, homeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email and password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, registerActivity.class));
            }
        });
    }
}
