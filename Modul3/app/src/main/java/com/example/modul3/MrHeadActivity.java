package com.example.modul3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MrHeadActivity extends AppCompatActivity {

    private ImageView hair, eyebrow, moustache, beard;
    private CheckBox checkHair, checkEyebrow, checkMoustache, checkBeard;
    private TextView emailView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrhead);

        // Inisialisasi ImageView
        hair = findViewById(R.id.hair);
        eyebrow = findViewById(R.id.eyebrow);
        moustache = findViewById(R.id.moustache);
        beard = findViewById(R.id.beard);

        // Inisialisasi CheckBox
        checkHair = findViewById(R.id.check_hair);
        checkEyebrow = findViewById(R.id.check_eyebrow);
        checkMoustache = findViewById(R.id.check_moustache);
        checkBeard = findViewById(R.id.check_beard);

        // Set checkbox tercentang secara default
        checkHair.setChecked(true);
        checkEyebrow.setChecked(true);
        checkMoustache.setChecked(true);
        checkBeard.setChecked(true);

        // Tambahkan event listener ke setiap checkbox
        checkHair.setOnCheckedChangeListener((buttonView, isChecked) ->
                hair.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));

        checkEyebrow.setOnCheckedChangeListener((buttonView, isChecked) ->
                eyebrow.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));

        checkMoustache.setOnCheckedChangeListener((buttonView, isChecked) ->
                moustache.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));

        checkBeard.setOnCheckedChangeListener((buttonView, isChecked) ->
                beard.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));

        // Inisialisasi TextView untuk email dan password
        emailView = findViewById(R.id.txtEmail);
        passwordView = findViewById(R.id.txtPassword);

        // Mengambil data dari Intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // Menampilkan email dan password pada TextView
        if (email != null && password != null) {
            emailView.setText("Hello, " + email);
            passwordView.setText("Your password: " + password);
        } else {
            emailView.setText("Email tidak tersedia!");
            passwordView.setText("Password tidak tersedia!");
        }

        // Tombol untuk pindah ke halaman ContactUs
        Button btnContactUs = findViewById(R.id.btnContactUs);
        btnContactUs.setOnClickListener(v -> {
            Intent contactIntent = new Intent(MrHeadActivity.this, ContactUsActivity.class);
            startActivity(contactIntent);
        });
    }
}
