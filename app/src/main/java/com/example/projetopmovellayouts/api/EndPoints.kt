package com.example.projetopmovellayouts.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("/api/info/getinfouserid/{id}")
    fun getInfoUser(@Path("id") id: Int): Call<InfoUser>

    @POST("/api/users/login")
    fun loginUser(@Body username: String?, password: String?): Call<LoginResponse>

    @GET("/getrotas")
    fun getRotas(): Call<List<Rotas>>

    @POST("/addsaldo")
    fun addSaldo(@Body saldoResponse: AddSaldoResponse): Call<Void>

    @GET("/getinfouserid/{userId}")
    fun getInfoUserId(@Path("userId") userId: Int): Call<InfoUser>

    @POST("/novacompras/{userId}/{rotasId}")
    fun addCompra(@Path("userId") userId: Int, @Path("rotasId") rotasId: Int): Call<Void>

    @GET("/getcompras")
    fun getCompras(): Call<List<Compras>>

}
