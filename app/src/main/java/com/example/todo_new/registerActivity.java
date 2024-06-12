package com.example.todo_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    EditText name, email, password, confirm_password;
    Button btn_login, btn_signup;
    CheckBox agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextTextName);
        email = findViewById(R.id.editTextTextEmail);
        password = findViewById(R.id.editTextTextPassword);
        confirm_password = findViewById(R.id.editTextTextConfirmPassword);
        btn_login = findViewById(R.id.buttonRegiLogin);
        btn_signup = findViewById(R.id.buttonRegiSignUp);
        agree = findViewById(R.id.checkBox);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerActivity.this, loginActivity.class));
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_name = name.getText().toString();
                String e_mail = email.getText().toString();
                String pwd = password.getText().toString();
                String con_pwd = confirm_password.getText().toString();
                Database db = new Database(getApplicationContext());

                Log.d("RegisterActivity", "Name: " + reg_name + ", Email: " + e_mail);

                if (reg_name.isEmpty() || e_mail.isEmpty() || pwd.isEmpty() || con_pwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd.equals(con_pwd)) {
                        if (isValid(pwd)) {
                            db.register(reg_name, e_mail, pwd);
                            Toast.makeText(getApplicationContext(), "Records Inserted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registerActivity.this, loginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Password must be exactly 8 characters, including letters, digits, and symbols!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords didn't match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValid(String password) {
        if (password.length() != 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
            }
        }

        return hasLetter && hasDigit && hasSymbol;
    }
}
