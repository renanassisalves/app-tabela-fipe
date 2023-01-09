package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterMarca
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterModelo
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Ano
import com.geardevelopmentbrazil.tabelafipe.models.Marca
import com.geardevelopmentbrazil.tabelafipe.models.Modelo
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ListagemModelo : AppCompatActivity() {

    lateinit var listaMarcas: List<Modelo>
    lateinit var listaAnos: List<Ano>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_modelo)

        val codigoMarca = intent.extras?.get("Codigo")
        val retrofit: Retrofit = Utils().getRetrofitInstance()
        val service = retrofit.create(FipeService::class.java)
        val callback = service.getModelos("carro", codigoMarca as Int)

        listaMarcas = List(0) {Modelo("", 1)}
        listaAnos = List(0) {Ano("", "")}

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

                    inicializarRecyclerView(listaMarcas)
                } else {
                    Utils().exibirErroConexao(applicationContext)
                }
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
