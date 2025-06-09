package com.example.firebasepraktikum

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertNoteActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var tvEmail: TextView
    lateinit var tvUid: TextView
    lateinit var btnKeluar: Button
    private var mAuth: FirebaseAuth? = null
    lateinit var etTitle: EditText
    lateinit var etDesc: EditText
    lateinit var btnSubmit: Button
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_note)

        tvEmail = findViewById(R.id.tv_email)
        tvUid = findViewById(R.id.tv_uid)
        btnKeluar = findViewById(R.id.btn_keluar)
        mAuth = FirebaseAuth.getInstance()
        etTitle = findViewById(R.id.et_title)
        etDesc = findViewById(R.id.et_description)
        btnSubmit = findViewById(R.id.btn_submit)

        firebaseDatabase = FirebaseDatabase.getInstance("https://pamfirebase-f4630-default-rtdb.firebaseio.com/")
        databaseReference = firebaseDatabase!!.reference
        note = Note()

        btnKeluar.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_keluar -> logOut()
            R.id.btn_submit -> submitData()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            tvEmail.text = currentUser.email
            tvUid.text = currentUser.uid
        }
    }

    private fun logOut() {
        mAuth!!.signOut()
        val intent = Intent(this@InsertNoteActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(etTitle.text.toString())) {
            etTitle.error = "Required"
            result = false
        } else {
            etTitle.error = null
        }

        if (TextUtils.isEmpty(etDesc.text.toString())) {
            etDesc.error = "Required"
            result = false
        } else {
            etDesc.error = null
        }

        return result
    }

    private fun submitData() {
        if (!validateForm()) return

        val title = etTitle.text.toString()
        val desc = etDesc.text.toString()

        note!!.title = title
        note!!.description = desc

        val currentUser = mAuth!!.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            databaseReference!!.child("Admin")
                .child(userId)
                .child("Notes")
                .push()
                .setValue(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    etTitle.setText("")
                    etDesc.setText("")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
