package com.brandonbryant.carfax.db

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestApi {
    private val carfaxApi: CarfaxAPIHelper
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://carfax-for-consumers.firebaseio.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        carfaxApi = retrofit.create(CarfaxAPIHelper::class.java)
    }

    fun getListings(): Call<CARFAXApiResponse>{
        return carfaxApi.getListings()
    }
}