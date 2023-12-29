package com.example.projetopmovellayouts.api

data class Rotas(
    val id: Int,
    val origem: String,
    val destino: String,
    val hora: String,
    val dia: String,
    val preço: Float
)
