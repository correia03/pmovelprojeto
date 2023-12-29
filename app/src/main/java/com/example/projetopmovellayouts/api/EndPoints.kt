package com.example.retrofit_26617.api


import com.example.projetopmovellayouts.api.AddSaldoResponse
import com.example.projetopmovellayouts.api.Compras
import com.example.projetopmovellayouts.api.InfoUser
import com.example.projetopmovellayouts.api.LoginResponse
import com.example.projetopmovellayouts.api.Rotas
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("/api/info/getInfoUser/{id}")
    fun getInfoUser(@Path("id") id: Int): Call<InfoUser>

    @POST("/login")
    fun loginUser(@Body loginResponse: LoginResponse): Call<Void>

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
