package com.example.todoapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.Database.SignupDB;

public class LoginActivity extends AppCompatActivity {
    EditText logEmail, logPassword;
    TextView text;
    public static final String SHARED_PREFS="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


                // checkBox();
                text = findViewById(R.id.textView8);
                logEmail = findViewById(R.id.editTextText4);
                logPassword = findViewById(R.id.editTextText5);

                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                        startActivity(intent);
                    }
                });
        checkBox();
            }

    private void checkBox() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check=sharedPreferences.getString("name","");
        if(check.equals("true")){
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),NotesActivity.class);
            startActivity(intent);
            finish();
        }
    }

            public void loginUser(View view) {
                String email = logEmail.getText().toString();
                String password = logPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return; // Do not proceed with login if fields are empty
                }

                SignupDB dbHelper = new SignupDB(this);
                boolean loggedIn = dbHelper.login(email, password);

                if (loggedIn) {

            SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("name","true");
            editor.apply();

                    Intent intent = new Intent(LoginActivity.this,NotesActivity.class);
                    intent.putExtra("key_email",email);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Email and password did not match", Toast.LENGTH_SHORT).show();
                }
            }
        }


