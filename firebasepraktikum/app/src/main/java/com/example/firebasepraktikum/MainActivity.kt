package com.example.firebasepraktikum

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btnLoginAuto: Button? = null // tombol autentikasi otomatis
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoginAuto = findViewById(R.id.btn_login_auto) // ambil referensi dari tombol baru
        mAuth = FirebaseAuth.getInstance()

        btnLoginAuto!!.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login_auto -> autoLogin() // tombol untuk login otomatis
        }
    }

    // Metode untuk autentikasi otomatis
    private fun autoLogin() {
        val email = "person1@gmail.com" // email yang sudah ditentukan
        val password = "person1" // password yang sudah ditentukan

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this@MainActivity, InsertNoteActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@MainActivity, "Log In First", Toast.LENGTH_SHORT).show()
        }
    }
}