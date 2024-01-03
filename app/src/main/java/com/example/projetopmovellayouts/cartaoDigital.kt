package com.example.projetopmovellayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.InfoUser
import com.example.projetopmovellayouts.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class cartaoDigital : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartao_digital)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getInfoUserId(1)
        call.enqueue(object : Callback<InfoUser> {
            override fun onResponse(call: Call<InfoUser>, response: Response<InfoUser>) {
                if (response.isSuccessful){
                    val c: InfoUser = response.body()!!
                    val saldo = findViewById<TextView>(R.id.saldo)
                    saldo.text = c.saldo.toString()
                }
            }
            override fun onFailure(call: retrofit2.Call<InfoUser>, t: Throwable) {
                Toast.makeText(this@cartaoDigital, "${t.message}", Toast.LENGTH_SHORT)
            }
        })
        //pedido get infouser id 1 colocar na textbox o saldo do cartao

    }
    fun carregarcartao(view: View) {
        val intent = Intent(this, carregar_cartao::class.java)

        // Start the new activity
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
    fun personalinfo(view: View) {
        val intent = Intent(this, Personalinfo::class.java)

        // Start the new activity
        startActivity(intent)
    }
}