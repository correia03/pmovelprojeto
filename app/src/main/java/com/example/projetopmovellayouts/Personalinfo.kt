package com.example.projetopmovellayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Personalinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalinfo)
    }
    fun cartao_digital(view: View) {
        val intent = Intent(this, cartaoDigital::class.java).apply {

        }
        startActivity(intent)
    }
    fun buyticket(view: View) {
        val intent = Intent(this, comprarBilhete::class.java)

        // Start the new activity
        startActivity(intent)
    }
    fun swaphistory(view: View) {
        val intent = Intent(this, History::class.java)

        // Start the new activity
        startActivity(intent)

    }

    fun Rotas(view: View) {
        val intent = Intent(this, Rotas::class.java)

        // Start the new activity
        startActivity(intent)
    }
}