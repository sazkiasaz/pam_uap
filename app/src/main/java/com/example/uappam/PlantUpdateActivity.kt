package com.example.uappam

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantUpdateActivity : AppCompatActivity() {

    private lateinit var etNamaTanaman: EditText
    private lateinit var etHarga: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnUpdateItem: Button

    private lateinit var plantName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_plant)

        etNamaTanaman = findViewById(R.id.et_nama_tanaman)
        etHarga = findViewById(R.id.et_harga)
        etDeskripsi = findViewById(R.id.et_deskripsi)
        btnUpdateItem = findViewById(R.id.btn_update_item)

        plantName = intent.getStringExtra("plant_name") ?: ""

        if (plantName.isEmpty()) {
            Toast.makeText(this, "Tanaman tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadPlantData(plantName)

        btnUpdateItem.setOnClickListener {
            val newName = etNamaTanaman.text.toString().trim()
            val newPrice = etHarga.text.toString().trim()
            val newDescription = etDeskripsi.text.toString().trim()

            if (newName.isEmpty() || newPrice.isEmpty() || newDescription.isEmpty()) {
                Toast.makeText(this, "Isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedPlant = Plant(
                name = newName,
                price = "Rp $newPrice",
                description = newDescription
            )

            updatePlant(plantName, updatedPlant)
        }
    }

    private fun loadPlantData(name: String) {
        ApiClient.apiService.getPlantByName(name).enqueue(object : Callback<Plant> {
            override fun onResponse(call: Call<Plant>, response: Response<Plant>) {
                if (response.isSuccessful && response.body() != null) {
                    val plant = response.body()!!
                    etNamaTanaman.setText(plant.name)
                    etHarga.setText(plant.price.replace("Rp ", ""))
                    etDeskripsi.setText(plant.description)
                } else {
                    Toast.makeText(this@PlantUpdateActivity, "Gagal mengambil data tanaman", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Plant>, t: Throwable) {
                Toast.makeText(this@PlantUpdateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updatePlant(oldName: String, updatedPlant: Plant) {
        ApiClient.apiService.updatePlant(oldName, updatedPlant).enqueue(object : Callback<Plant> {
            override fun onResponse(call: Call<Plant>, response: Response<Plant>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PlantUpdateActivity, "Tanaman berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@PlantUpdateActivity, "Gagal memperbarui tanaman", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Plant>, t: Throwable) {
                Toast.makeText(this@PlantUpdateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
