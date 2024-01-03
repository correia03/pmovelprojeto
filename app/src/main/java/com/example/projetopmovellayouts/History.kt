package com.example.projetopmovellayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetopmovellayouts.adapter.HistoricoAdapter
import com.example.projetopmovellayouts.adapter.UserAdapter
import com.example.projetopmovellayouts.api.Compras
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.RotasInfo
import com.example.projetopmovellayouts.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getCompras()
        call.enqueue(object : Callback<List<Compras>> {
            override fun onResponse(call: Call<List<Compras>>, response: Response<List<Compras>>) {
                if (response.isSuccessful) {
                    (findViewById<RecyclerView>(R.id.recyclerView2)).apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@History)
                        adapter = HistoricoAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<Compras>>, t: Throwable) {
                Toast.makeText(this@History, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun buyticket(view: View) {
        val intent = Intent(this, comprarBilhete::class.java)

        // Start the new activity
        startActivity(intent)
    }
    fun cartao_digital(view: View) {
        val intent = Intent(this, cartaoDigital::class.java).apply {

        }
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