package com.example.mykalkulator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText1 = findViewById<EditText>(R.id.editTextNumber1)
        val editText2 = findViewById<EditText>(R.id.editTextNumber2)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val buttonHitung = findViewById<Button>(R.id.buttonHitung)

        buttonHitung.setOnClickListener {
            val num1 = editText1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = editText2.text.toString().toDoubleOrNull() ?: 0.0
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val operasi = selectedRadioButton?.text.toString()

            val hasil = when (operasi) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                else -> 0.0
            }

            // Kirim hasil ke ResultActivity
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("hasil", hasil)
            startActivity(intent)
        }
    }
}
