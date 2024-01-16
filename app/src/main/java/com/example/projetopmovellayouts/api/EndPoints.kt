package com.example.projetopmovellayouts.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("/api/info/getinfouserid/{id}")
    fun getInfoUser(@Path("id") id: Int): Call<InfoUser>

    @POST("/api/users/login")
    fun loginUser(@Body request:LoginRequest): Call<LoginResponse>

    @GET("api/rotas/getrotas")
    fun getRotas(): Call<List<RotasInfo>>

    @GET("api/info/getinfouserid/{userId}")
    fun getInfoUserId(@Path("userId") userId: Int): Call<InfoUser>

    @POST("api/compras/addcompra")
    fun fazerCompra(@Body compra: Compras): Call<Compras>

    @GET("api/compras/getcompras")
    fun getCompras(): Call<List<Compras>>

    @POST("api/info/addsaldo")
    fun addSaldo(@Body saldo: AddSaldoResponse): Call<AddSaldoResponse>

    @POST("api/paragens/getparagens")
    fun getParagens(@Body request:ParagensRequest): Call<List<ParagemResponse>>
}
