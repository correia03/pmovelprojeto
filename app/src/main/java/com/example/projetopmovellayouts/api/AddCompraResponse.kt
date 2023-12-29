package com.example.projetopmovellayouts.api

data class AddCompraResponse(
    val id: Int, // Assuming id is returned for the new compra, adjust as needed
    val userId: Int,
    val rotasId: Int,
    val ano: Int,
    val mes: Int,
    val dia: Int
)
