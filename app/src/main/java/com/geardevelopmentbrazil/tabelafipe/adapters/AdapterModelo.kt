package com.geardevelopmentbrazil.tabelafipe.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.activities.ListagemAnoActivity
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar
import com.geardevelopmentbrazil.tabelafipe.models.Modelo

class AdapterModelo(private val listaModelos: List<Modelo>):
        RecyclerView.Adapter<AdapterModelo.ViewHolder>() {
    var listaAtual: List<Modelo> = listaModelos
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewModelo: TextView
        init {
            textViewModelo = view.findViewById(R.id.textMarca)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_marca, viewGroup, false)
        return AdapterModelo.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewModelo.text = this.listaAtual[position].retornarNome()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ListagemAnoActivity::class.java)
            Auxiliar.setCodigoModelo(this.listaAtual[position].retornarCodigo())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaAtual.size
    }

    fun pesquisarLista(pesquisa: String) {
        listaAtual = listaModelos.filter {
            it.retornarNome().contains(pesquisa, true)
        }
        notifyDataSetChanged()
    }

    fun redefinirLista() {
        listaAtual = listaModelos
        notifyDataSetChanged()
    }
}