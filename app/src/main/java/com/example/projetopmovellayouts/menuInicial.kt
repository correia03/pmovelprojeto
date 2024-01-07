package com.example.projetopmovellayouts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.InfoUser
import com.example.projetopmovellayouts.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class menuInicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_inicial)
        val sharedPreferences = getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val storedUserId = sharedPreferences.getString("token", "")
        val userId: Int = try {
            storedUserId?.toInt() ?: 0 // If the storedUserId is null, use a default value of 0
        } catch (e: NumberFormatException) {
            // Handle the case where the storedUserId cannot be converted to an integer
            // You may want to log an error or provide a default value as needed
            0
        }
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getInfoUserId(userId)
        call.enqueue(object : Callback<InfoUser> {
            override fun onResponse(call: Call<InfoUser>, response: Response<InfoUser>) {
                if (response.isSuccessful){
                    val c: InfoUser = response.body()!!
                    val nome = findViewById<TextView>(R.id.welcomeText)

                    nome.text = c.nome

                }
            }

            override fun onFailure(call: retrofit2.Call<InfoUser>, t: Throwable) {
                Toast.makeText(this@menuInicial, "${t.message}", Toast.LENGTH_SHORT)
            }
        })

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

    fun personalinfo(view: View) {
        val intent = Intent(this, Personalinfo::class.java)

        // Start the new activity
        startActivity(intent)
    }
}