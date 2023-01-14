package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterModelo
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ListagemModeloActivity : AppCompatActivity() {

    var listaMarcas: List<Modelo>? = null
    var listaAnos: List<Ano>? = null

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_modelo)

        val bannerSuperior: AdView = findViewById(R.id.bannerModeloSuperior)
        val bannerInferior: AdView = findViewById(R.id.bannerModeloInferior)
        val adRequest = AdRequest.Builder().build()
        bannerSuperior.loadAd(adRequest)
        bannerInferior.loadAd(adRequest)

        val codigoMarca = Auxiliar.getCodigoMarca()
        val tipoSelecionado = Auxiliar.getTipo()
        val retrofit: Retrofit = Utils().getRetrofitInstance()
        val service = retrofit.create(FipeService::class.java)
        val callback = service.getModelos(tipoSelecionado, codigoMarca)

        progressBar = findViewById(R.id.progressBarModelo)

        callback.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Utils().exibirErroConexao(applicationContext)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var jsonResultante: JsonObject? = response.body()
                if (jsonResultante != null) {
                    var modelos: JsonArray = jsonResultante["modelos"] as JsonArray
                    var anos: JsonArray = jsonResultante["anos"] as JsonArray
                    val gson = GsonBuilder().create()
                    listaMarcas = gson.fromJson(modelos, Array<Modelo>::class.java).toList()
                    listaAnos = gson.fromJson(anos, Array<Ano>::class.java).toList()

                    inicializarRecyclerView(listaMarcas!!)
                } else {
                    Utils().exibirErroConexao(applicationContext)
                }

                progressBar?.visibility = View.GONE
            }
        })
    }


    fun inicializarRecyclerView(listaModelos: List<Modelo>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_listagem)
        val pesquisar = findViewById<SearchView>(R.id.pesquisar)
        val adapter = AdapterModelo(listaModelos)

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
