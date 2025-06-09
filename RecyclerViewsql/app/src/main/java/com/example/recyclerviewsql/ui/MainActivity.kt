package com.example.recyclerviewsql.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recyclerviewsql.database.AppDatabase
import com.example.recyclerviewsql.database.ItemDao
import com.example.recyclerviewsql.database.ItemEntity
import com.practicum.litesql.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDao
    private val itemList = mutableListOf<ItemEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "item-database"
        ).build()
        itemDao = database.itemDao()

        adapter = ItemAdapter(itemList) { position ->
            val item = itemList[position]
            lifecycleScope.launch {
                itemDao.delete(item)
                loadItemsFromDatabase()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener {
            val inputText = binding.editTextInput.text.toString()
            if (inputText.isNotBlank()) {
                val newItem = ItemEntity(name = inputText)
                lifecycleScope.launch {
                    itemDao.insert(newItem)
                    withContext(Dispatchers.Main) {
                        binding.editTextInput.text.clear()
                    }
                    loadItemsFromDatabase()
                }
            } else {
                Toast.makeText(this, "Teks tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        loadItemsFromDatabase()
    }

    private fun loadItemsFromDatabase() {
        lifecycleScope.launch {
            val items = itemDao.getAll()
            itemList.clear()
            itemList.addAll(items)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}

