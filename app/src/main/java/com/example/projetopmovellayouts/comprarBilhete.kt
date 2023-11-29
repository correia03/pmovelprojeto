package com.example.projetopmovellayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView

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