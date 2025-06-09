package com.example.uappam

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("plant/all")
    fun getAllPlants(): Call<PlantListResponse>

    @GET("plant/{id}")
    fun getPlantById(@Path("id") id: Int): Call<Plant>

    @GET("plant/name/{plant_name}")
    fun getPlantByName(@Path("plant_name") plantName: String): Call<Plant>

    @POST("plant/new")
    fun createPlant(@Body plant: Plant): Call<Plant>

    @PUT("plant/{plant_name}")
    fun updatePlant(@Path("plant_name") plantName: String, @Body plant: Plant): Call<Plant>

    @DELETE("plant/{plant_name}")
    fun deletePlant(@Path("plant_name") plantName: String): Call<Void>
}
