package com.example.projetopmovellayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetopmovellayouts.adapter.UserAdapter
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.ServiceBuilder
import com.example.projetopmovellayouts.api.RotasInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class comprarBilhete : AppCompatActivity() {
    lateinit var calendarView: CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar_bilhete)
        calendarView = findViewById(R.id.calendarView)

        calendarView
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)


                })
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getRotas()
        call.enqueue(object : Callback<List<RotasInfo>> {
            override fun onResponse(call: Call<List<RotasInfo>>, response: Response<List<RotasInfo>>) {
                if (response.isSuccessful) {
                    (findViewById<RecyclerView>(R.id.recyclerView)).apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@comprarBilhete)
                        adapter = UserAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<RotasInfo>>, t: Throwable) {
                Toast.makeText(this@comprarBilhete, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun swaphistory(view: View) {
        val intent = Intent(this, History::class.java)

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