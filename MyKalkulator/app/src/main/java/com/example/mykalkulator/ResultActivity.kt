package com.example.mykalkulator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result) // Pastikan file activity_result.xml ada di res/layout

        // Ambil data hasil dari Intent
        val result = intent.getDoubleExtra("result", 0.0)

        // Ambil referensi TextView dari layout
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val textViewNim = findViewById<TextView>(R.id.textViewNim)
        val textViewNama = findViewById<TextView>(R.id.textViewNama)

        // Tampilkan hasil, NIM, dan Nama
        textViewResult.text = "Hasil: $result"
        textViewNim.text = "NIM: 235150701111017"
        textViewNama.text = "Nama: Sazkia Sabrina Aura Zahira"
    }
}
