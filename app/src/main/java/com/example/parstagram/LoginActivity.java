package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    // VIEW
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Since we defined LoginActivity to launch on app
        // startup, LoginActivity will show up on every app
        // launch even if user was already logged in previously.
        // Thus, if user has already logged in before then app will
        // forward them to MainActivity.
        if(ParseUser.getCurrentUser() != null) {
            goToMainAcitivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    public void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            // Parse will always pass in an exception object and
            // if the request succeeded, the object will be null.
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Login failed", e);
                    return;
                }
                goToMainAcitivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToMainAcitivity() {
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
        finish();
    }
}