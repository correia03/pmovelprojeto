package com.example.projetopmovellayouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class carregar_cartao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carregar_cartao)
    }
    fun voltartras(view: View) {
        finish()
    }

}