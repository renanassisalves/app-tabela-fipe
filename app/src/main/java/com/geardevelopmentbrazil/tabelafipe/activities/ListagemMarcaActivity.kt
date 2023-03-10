package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.models.Marca
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterMarca
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class ListagemMarcaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_marca)

        val bannerSuperior: AdView = findViewById(R.id.bannerMarcaSuperior)
        val bannerInferior: AdView = findViewById(R.id.bannerMarcaInferior)
        val adRequest = AdRequest.Builder().build()
        bannerSuperior.loadAd(adRequest)
        bannerInferior.loadAd(adRequest)

        val listaObjetos = intent.extras?.get("MARCAS") as List<Marca>
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_listagem)
        val pesquisar = findViewById<SearchView>(R.id.pesquisar)
        val adapter = AdapterMarca(listaObjetos)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        pesquisar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    adapter.pesquisarLista(query)
                } else {
                    adapter.redefinirLista()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    adapter.pesquisarLista(query)
                } else  {
                    adapter.redefinirLista()
                }
                return true
            }
        })
    }
}