package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Ano
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import com.geardevelopmentbrazil.tabelafipe.models.Veiculo
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VeiculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculo)

        val tipoSelecionado = Auxiliar.getTipo()
        val codigoMarca = Auxiliar.getCodigoMarca()
        val codigoModelo = Auxiliar.getCodigoModelo()
        val codigoAno = Auxiliar.getCodigoAno()

        val retrofit: Retrofit = Utils().getRetrofitInstance()
        val service = retrofit.create(FipeService::class.java)
        val callback = service.getVeiculo(tipoSelecionado,
            codigoMarca,
            codigoModelo,
            codigoAno)

        callback.enqueue(object: Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Utils().exibirErroConexao(applicationContext)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var jsonResultante: JsonObject? = response.body()
                val tipoSelecionado = Auxiliar.getTipo()
                val codigoMarca = Auxiliar.getCodigoMarca()
                val codigoModelo = Auxiliar.getCodigoModelo()
                val codigoAno = Auxiliar.getCodigoAno()
                if (jsonResultante != null) {
                    val gson = GsonBuilder().create()
                    val veiculo = gson.fromJson(jsonResultante, Veiculo::class.java)

                    println()
                } else {
                    Utils().exibirErroConexao(applicationContext)
                }
            }
        })
    }
}