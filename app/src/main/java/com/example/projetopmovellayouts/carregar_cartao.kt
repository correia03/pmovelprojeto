package com.example.projetopmovellayouts

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.projetopmovellayouts.api.AddSaldoResponse
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class carregar_cartao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carregar_cartao)
    }
    fun voltartras(view: View) {
        finish()
    }

    fun carregar(view: View) {
        val sharedPreferences = getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val storedUserId = sharedPreferences.getString("token", "")
        val userId: Int = try {
            storedUserId?.toInt() ?: 0 // If the storedUserId is null, use a default value of 0
        } catch (e: NumberFormatException) {
            // Handle the case where the storedUserId cannot be converted to an integer
            // You may want to log an error or provide a default value as needed
            0
        }
        val saldo = findViewById<EditText>(R.id.quantia).text.toString().toFloat()
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.addSaldo(AddSaldoResponse(userId, saldo))
        call.enqueue(object : Callback<AddSaldoResponse> {
            override fun onResponse(call: Call<AddSaldoResponse>, response: Response<AddSaldoResponse>) {
                if (response.isSuccessful){
                    val c: AddSaldoResponse = response.body()!!
                    Toast.makeText(this@carregar_cartao, "Saldo carregado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            override fun onFailure(call: retrofit2.Call<AddSaldoResponse>, t: Throwable) {
                Toast.makeText(this@carregar_cartao, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}