package com.example.projetopmovellayouts.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()

    // Replace "http://localhost:4242/" with your actual base URL
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.16.219.199:4242/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
