package com.example.todoapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.Database.SignupDB;

public class SignupActivity extends AppCompatActivity {
    TextView text;
    Button accountCreate;
    EditText signName, signEmail, signPassword;
    SignupDB dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

                signName = findViewById(R.id.editTextText1);
                signEmail = findViewById(R.id.editTextText4);
                signPassword = findViewById(R.id.editTextText5);

                dbHelper = new SignupDB(this);

                text = findViewById(R.id.textView8);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

                accountCreate = findViewById(R.id.button2);
                accountCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // It's generally better to call your registerUser method here
                        registerUser(view);
                    }
                });
            }

            public void registerUser(View view) {
                String name1 = signName.getText().toString();
                String email1 = signEmail.getText().toString();
                String pass1 = signPassword.getText().toString();
                boolean registeredSuccess = dbHelper.registerUserHelper(name1, email1, pass1);
                if (name1.equals("") || email1.equals("") || pass1.equals("")) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
       /* else if (dbHelper.checkUsername(name1)) {
                Toast.makeText(SignupActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
            }*/

                // boolean registeredSuccess = dbHelper.registerUserHelper(name1, email1, pass1);
                else if (registeredSuccess) {
                    Toast.makeText(this, "User Registered Successfully...!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                }



                // Clear the input fields after registration
                signName.setText("");
                signEmail.setText("");
                signPassword.setText("");
            }
        }









