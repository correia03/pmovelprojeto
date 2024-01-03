package com.example.projetopmovellayouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.projetopmovellayouts.R
import com.example.projetopmovellayouts.api.Compras


class HistoricoAdapter(val Compras: List<Compras>): RecyclerView.Adapter<ComprasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComprasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerline2, parent, false)
        return ComprasViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Compras.size
    }

    override fun onBindViewHolder(holder: ComprasViewHolder, position: Int) {
        return holder.bind(Compras[position])
    }
}

class ComprasViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    private val novo: Button = itemView.findViewById(R.id.btnHistorico)

    fun bind(Compras: Compras) {
        novo.text = "Rota nº: " + Compras.rotasId.toString() + " Comprado a: " + Compras.ano.toString() + " - " + Compras.mes.toString() + " - " + Compras.dia.toString()
        }
}