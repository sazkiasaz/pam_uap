package com.example.uappam

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlantActivity : AppCompatActivity() {

    private lateinit var etNamaTanaman: EditText
    private lateinit var etHarga: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnTambahItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        etNamaTanaman = findViewById(R.id.et_nama_tanaman)
        etHarga = findViewById(R.id.et_harga)
        etDeskripsi = findViewById(R.id.et_deskripsi)
        btnTambahItem = findViewById(R.id.btn_tambah_item)

        btnTambahItem.setOnClickListener {
            val nama = etNamaTanaman.text.toString().trim()
            val hargaInput = etHarga.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()

            if (nama.isEmpty() || hargaInput.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi dengan benar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Format harga jadi string, misal "Rp 100000"
            val harga = "Rp $hargaInput"

            val newPlant = Plant(
                name = nama,
                price = harga,
                description = deskripsi,
                imageResId = null // karena tidak ada input gambar di sini
            )

            tambahTanaman(newPlant)
        }
    }

    private fun tambahTanaman(plant: Plant) {
        ApiClient.apiService.createPlant(plant).enqueue(object : Callback<Plant> {
            override fun onResponse(call: Call<Plant>, response: Response<Plant>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddPlantActivity, "Tanaman berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddPlantActivity, "Gagal menambahkan tanaman", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Plant>, t: Throwable) {
                Toast.makeText(this@AddPlantActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
