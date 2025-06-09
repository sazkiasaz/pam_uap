package com.example.uappam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvPlants: RecyclerView
    private lateinit var plantAdapter: PlantAdapter
    private val plantList = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView
        rvPlants = findViewById(R.id.rv_plants)
        rvPlants.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter dan listener
        plantAdapter = PlantAdapter(
            plantList,
            onDetailClick = { plant ->
                val intent = Intent(this, PlantUpdateActivity::class.java)
                intent.putExtra("plant_name", plant.name)
                startActivity(intent)
            },
            onDeleteClick = { position ->
                val plantName = plantList[position].name
                deletePlant(plantName, position)
            }
        )
        rvPlants.adapter = plantAdapter

        // Tombol tambah
        val btnTambah = findViewById<MaterialButton>(R.id.btn_tambah_list)
        btnTambah.setOnClickListener {
            val intent = Intent(this, AddPlantActivity::class.java)
            startActivity(intent)
        }

        // Load data tanaman
        loadPlants()
    }

    private fun loadPlants() {
        ApiClient.apiService.getAllPlants().enqueue(object : Callback<PlantListResponse> {
            override fun onResponse(
                call: Call<PlantListResponse>,
                response: Response<PlantListResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    plantList.clear()
                    plantList.addAll(response.body()!!.data)
                    plantAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data tanaman", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PlantListResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deletePlant(plantName: String, position: Int) {
        ApiClient.apiService.deletePlant(plantName).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Berhasil menghapus $plantName", Toast.LENGTH_SHORT).show()
                    plantAdapter.removeItem(position)
                } else {
                    Toast.makeText(this@MainActivity, "Gagal menghapus $plantName", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadPlants()
    }
}
