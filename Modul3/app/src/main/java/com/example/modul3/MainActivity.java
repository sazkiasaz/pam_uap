package com.example.modul3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            String correctEmail = "alfatadzaky@gmail.com";
            String correctPassword = "235150707111008";

            if (email.equals(correctEmail) && password.equals(correctPassword)) {
                Intent intent = new Intent(MainActivity.this, MrHeadActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Email atau Password salah!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
