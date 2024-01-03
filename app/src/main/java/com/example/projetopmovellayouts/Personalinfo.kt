package com.example.projetopmovellayouts

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

class Personalinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalinfo)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getInfoUserId(1)
        call.enqueue(object : Callback<InfoUser> {
            override fun onResponse(call: Call<InfoUser>, response: Response<InfoUser>) {
                if (response.isSuccessful){
                    val c: InfoUser = response.body()!!
                    val nome = findViewById<TextView>(R.id.nomeedit)
                    val email = findViewById<TextView>(R.id.editTextmail)
                    val telemovel = findViewById<TextView>(R.id.editText7)
                    val escola = findViewById<TextView>(R.id.editText3)
                    val curso = findViewById<TextView>(R.id.editText5)
                    val nif = findViewById<TextView>(R.id.editText6)
                    val niss = findViewById<TextView>(R.id.editText8)
                    val numero = findViewById<TextView>(R.id.editText4)

                    nome.text = c.nome
                    email.text = c.email
                    telemovel.text = c.telefone.toString()
                    escola.text = c.escola
                    curso.text = c.curso
                    nif.text = c.nif.toString()
                    niss.text = c.niss.toString()
                    numero.text = c.numeroaluno.toString()



                }
            }
            override fun onFailure(call: retrofit2.Call<InfoUser>, t: Throwable) {
                Toast.makeText(this@Personalinfo, "${t.message}", Toast.LENGTH_SHORT)
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
}