package com.geardevelopmentbrazil.tabelafipe.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.activities.ListagemModeloActivity
import com.geardevelopmentbrazil.tabelafipe.activities.VeiculoActivity
import com.geardevelopmentbrazil.tabelafipe.models.Ano
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar

class AdapterAno(private val listaAnos: List<Ano>):
    RecyclerView.Adapter<AdapterAno.ViewHolder>() {
    var listaAtual: List<Ano> = listaAnos

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAno: TextView

        init {
            textViewAno = view.findViewById(R.id.textMarca)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_marca, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewAno.text = this.listaAtual[position].retornarAno()
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, VeiculoActivity::class.java)
            Auxiliar.setCodigoAno(this.listaAtual[position].retornarCodigo())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaAtual.size
    }

    fun pesquisarLista(pesquisa: String) {
        listaAtual = listaAnos.filter {
            it.retornarAno().contains(pesquisa, true)
        }
        notifyDataSetChanged()
    }

    fun redefinirLista() {
        listaAtual = listaAnos
        notifyDataSetChanged()
    }
}