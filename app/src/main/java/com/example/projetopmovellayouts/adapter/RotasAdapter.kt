package com.example.projetopmovellayouts.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projetopmovellayouts.R
import com.example.projetopmovellayouts.api.Compras
import com.example.projetopmovellayouts.api.RotasInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.ServiceBuilder
import java.util.Calendar


class UserAdapter(val RotasInfo: List<RotasInfo>): RecyclerView.Adapter<RotasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerline, parent, false)
        return RotasViewHolder(view)
    }

    override fun getItemCount(): Int {
        return RotasInfo.size
    }

    override fun onBindViewHolder(holder: RotasViewHolder, position: Int) {
        return holder.bind(RotasInfo[position])
    }
}

class RotasViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    private val novo: Button = itemView.findViewById(R.id.firstoption)


    fun bind(RotasInfo: RotasInfo){
        novo.text = RotasInfo.origem +" - " + RotasInfo.destino + " - " + RotasInfo.hora + " - " + RotasInfo.dia + " - " + RotasInfo.preço.toString() + "€"
    novo.setOnClickListener(View.OnClickListener {
        val compra = Compras( getUserId(), RotasInfo.id, getCurrentYear(), getCurrentMonth(), getCurrentDay())
        //codigo para comprar bilhete)
        val request: EndPoints = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.fazerCompra(compra)
        call.enqueue(object : Callback<Compras> {
            override fun onResponse(call: Call<Compras>, response: Response<Compras>) {
                if (response.isSuccessful) {
                    println("Compra feita com sucesso")
                    //toast para dizer que a compra foi feita com sucesso
                    Toast.makeText(itemView.context, "Compra feita com sucesso", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Compras>, t: Throwable) {
                println("Erro ao fazer compra")
                //toast para dizer que a compra foi feita sem sucesso
                Toast.makeText(itemView.context, "Erro ao fazer compra", Toast.LENGTH_SHORT).show()
            }
        })



    })
    }



    private fun getUserId(): Int {
        return 1
    }

    private fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    private fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1  // Months are 0-indexed in Calendar
    }

    private fun getCurrentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }
}