package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        this.context = this;

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.equals("dani") && password.equals("1234")) {
                    Intent intent = new Intent(context, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login fallito
                    Toast.makeText(Login.this, "Login fallito", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
