package com.geardevelopmentbrazil.tabelafipe.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.models.Ano
import com.geardevelopmentbrazil.tabelafipe.models.Marca

class AdapterAno(private val listaAnos: List<Ano>):
    RecyclerView.Adapter<AdapterAno.ViewHolder>() {
    var listaAtual: List<Ano> = listaAnos

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAno: TextView

        init {
            textViewAno = view.findViewById(R.id.textMarca)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAno.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AdapterAno.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}