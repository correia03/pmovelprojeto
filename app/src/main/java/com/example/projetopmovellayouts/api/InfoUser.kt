package com.example.projetopmovellayouts.api

data class InfoUser(
    val id: Int,
    val nome: String,
    val email: String,
    val escola: String,
    val curso: String,
    val numeroaluno: Int,
    val nif: Int,
    val telefone: Int,
    val saldo: Float,
    val niss: Int,
    val userId: Int
)