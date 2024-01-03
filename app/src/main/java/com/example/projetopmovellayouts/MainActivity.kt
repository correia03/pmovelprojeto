package com.example.projetopmovellayouts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.InfoUser
import com.example.projetopmovellayouts.api.LoginRequest
import com.example.projetopmovellayouts.api.LoginResponse
import com.example.projetopmovellayouts.api.ServiceBuilder
import com.example.projetopmovellayouts.api.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var endPoints: EndPoints
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Create the EndPoints interface using ServiceBuilder
        endPoints = ServiceBuilder.buildService(EndPoints::class.java)
    }
    fun login (view: View) {
        //start new activity name menuInicial
        //val intent = Intent(this, menuInicial::class.java)
        //startActivity(intent)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Check if username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }

       val request = ServiceBuilder.buildService(EndPoints::class.java)
        //console log username e password
        println("username: $username")
        println("password: $password")
        val loginRequest = LoginRequest(username,password)
        val call = request.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Save token in SharedPreferences upon successful login
                    val token = response.body()?.token ?: ""
                    saveTokenInSharedPreferences(token)

                    // Start the menuInicial activity
                    val intent = Intent(this@MainActivity, menuInicial::class.java)
                    startActivity(intent)
                } else {
                    // Show a toast message for unsuccessful login
                    Toast.makeText(
                        this@MainActivity,
                        "Username or password is incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Log the error message
                Log.e("Login", "Error: ${t.message}")
                // Handle failure, e.g., network issues
                Toast.makeText(
                    this@MainActivity,
                    "Login failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
   private fun saveTokenInSharedPreferences(token: String) {
        val sharedPreferences = getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
}