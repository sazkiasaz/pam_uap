package com.example.kalkulator;

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
public class  ResultActivity extends AppCompatActivity{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EdgeToEdge.enable(this)
        setContentView(R.layout.activity_input)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inputLayout)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNumber1 = findViewById<EditText>(R.id.etNumber1)
                val etNumber2 = findViewById<EditText>(R.id.etNumber2)
                val btnCalculate = findViewById<Button>(R.id.btnCalculate)

                val rbAdd = findViewById<RadioButton>(R.id.rbAdd)
                val rbSubtract = findViewById<RadioButton>(R.id.rbSubtract)
                val rbMultiply = findViewById<RadioButton>(R.id.rbMultiply)
                val rbDivide = findViewById<RadioButton>(R.id.rbDivide)

                btnCalculate.setOnClickListener {
            val number1 = etNumber1.text.toString().toDouble()
            val number2 = etNumber2.text.toString().toDouble()
            var result = 0.0

            when {
                rbAdd.isChecked -> result = number1 + number2
                rbSubtract.isChecked -> result = number1 - number2
                rbMultiply.isChecked -> result = number1 * number2
                rbDivide.isChecked -> result = number1 / number2
            }

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("result", result)
            intent.putExtra("name", "Nama Anda")
            intent.putExtra("nim", "123456789")
            startActivity(intent)
        }
    }
}
