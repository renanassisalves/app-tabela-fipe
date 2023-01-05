package com.geardevelopmentbrazil.tabelafipe.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.models.Marca

class RecyclerMarcas(private val listaMarcas: List<Marca>) :
    RecyclerView.Adapter<RecyclerMarcas.ViewHolder>() {
    var listaAtual: List<Marca> = listaMarcas

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewMarca: TextView
        init {
            textViewMarca = view.findViewById(R.id.textMarca)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
         val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_marca, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewMarca.text = this.listaAtual[position].retornarMarca()
    }

    override fun getItemCount(): Int {
        return listaAtual.size
    }

    fun pesquisarLista(pesquisa: String) {
        listaAtual = listaMarcas.filter {
            it.retornarMarca().contains(pesquisa, true)
        }
        notifyDataSetChanged()
    }

    fun redefinirLista() {
        listaAtual = listaMarcas
        notifyDataSetChanged()
    }

}