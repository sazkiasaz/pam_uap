package com.example.firebaseprak

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
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var btnMasuk: Button
    lateinit var btnDaftar: Button
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_pass)
        mAuth = FirebaseAuth.getInstance()
        btnMasuk = findViewById(R.id.btn_masuk)
        btnDaftar = findViewById(R.id.btn_daftar)
        btnMasuk.setOnClickListener(this)
        btnDaftar.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_masuk -> login(etEmail.text.toString(), etPass.text.toString())
            R.id.btn_daftar -> signup(etEmail.text.toString(), etPass.text.toString())
        }
    }

    fun login(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    Toast.makeText(this@MainActivity, user.toString(), Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else { // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun signup(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    updateUI(user)
                    Toast.makeText(this@MainActivity, user.toString(), Toast.LENGTH_SHORT).show()
                } else { // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this@MainActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(etEmail!!.text.toString())) {
            etEmail!!.error = "Required"
            result = false
        } else {
            etEmail!!.error = null
        }
        if (TextUtils.isEmpty(etPass!!.text.toString())) {
            etPass!!.error = "Required"
            result = false
        } else {
            etPass!!.error = null
        }
        return result
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this@MainActivity, InsertNoteActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@MainActivity, "Log In First", Toast.LENGTH_SHORT).show()
        }
    }
}