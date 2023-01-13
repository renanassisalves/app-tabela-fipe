package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterAno
import com.geardevelopmentbrazil.tabelafipe.adapters.AdapterModelo
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Ano
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar
import com.geardevelopmentbrazil.tabelafipe.models.Modelo
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ListagemAnoActivity : AppCompatActivity() {

     var listaAnos: List<Ano>
     var progressAno: ProgressBar? = null

    init {
        listaAnos = MutableList(0) {Ano("", "")}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_ano)


        progressAno = findViewById(R.id.progressAno)
        val codigoMarca = Auxiliar.getCodigoMarca()
        val codigoModelo = Auxiliar.getCodigoModelo()
        val tipoSelecionado = Auxiliar.getTipo()

        val retrofit: Retrofit = Utils().getRetrofitInstance()
        val service = retrofit.create(FipeService::class.java)
        val callback = service.getAnos(tipoSelecionado,
                                        codigoMarca,
                                        codigoModelo)

        callback.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Utils().exibirErroConexao(applicationContext)
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                var jsonResultante: JsonArray? = response.body()
                if (jsonResultante != null) {
                    val gson = GsonBuilder().create()
                    listaAnos = gson.fromJson(jsonResultante, Array<Ano>::class.java).toList()

                    inicializarRecyclerView(listaAnos)
                } else {
                    Utils().exibirErroConexao(applicationContext)
                }

                progressAno?.visibility = View.GONE
                println()
            }
        })
    }

    fun inicializarRecyclerView(listaAnos: List<Ano>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_listagem)
        val pesquisar = findViewById<SearchView>(R.id.pesquisar)
        val adapter = AdapterAno(listaAnos)

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