package com.geardevelopmentbrazil.tabelafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import com.geardevelopmentbrazil.tabelafipe.models.Veiculo
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VeiculoActivity : AppCompatActivity() {

    var imagemTipo: ImageView? = null

    var textAnoModelo: TextView? = null
    var textCodigoFipe: TextView? = null
    var textCombustivel: TextView? = null
    var textMarca: TextView? = null
    var textMesReferencia: TextView? = null
    var textModelo: TextView? = null
    var textSiglaCombustivel: TextView? = null
    var textTipoVeiculo: TextView? = null
    var textValor: TextView? = null
    var progressBar: ProgressBar? = null

    var veiculo: Veiculo? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculo)

        imagemTipo = findViewById(R.id.imagemTipoVeiculo)
        textAnoModelo = findViewById(R.id.anoModelo)
        textCodigoFipe = findViewById(R.id.codigoFipe)
        textCombustivel = findViewById(R.id.combustivel)
        textMarca = findViewById(R.id.marca)
        textMesReferencia = findViewById(R.id.mesReferencia)
        textModelo = findViewById(R.id.modelo)
        textSiglaCombustivel = findViewById(R.id.siglaCombustivel)
        textTipoVeiculo = findViewById(R.id.tipoVeiculo)
        textValor = findViewById(R.id.valor)
        progressBar = findViewById(R.id.progressBarVeiculo)

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
                if (jsonResultante != null) {
                    val gson = GsonBuilder().create()
                    veiculo = gson.fromJson(jsonResultante, Veiculo::class.java)

                    textAnoModelo?.setText(veiculo?.AnoModelo)
                    textCodigoFipe?.setText(veiculo?.CodigoFipe)
                    textCombustivel?.setText(veiculo?.Combustivel)
                    textMarca?.setText(veiculo?.Marca)
                    textModelo?.setText(veiculo?.Modelo)
                    textSiglaCombustivel?.setText(veiculo?.SiglaCombustivel)
                    textMesReferencia?.setText(veiculo?.MesReferencia)
                    textValor?.setText(veiculo?.Valor)

                    definirTipoVeiculo()

                    progressBar?.visibility = View.INVISIBLE
                } else {
                    Utils().exibirErroConexao(applicationContext)
                }
            }
        })
    }

    fun definirTipoVeiculo(){
        var tipo = when(veiculo?.TipoVeiculo!!) {
            1 -> "Carro"
            2 -> "Moto"
            3 -> "Caminhão"
            else -> {"Carro"}
        }

        textTipoVeiculo?.setText(tipo)

        if (tipo.equals("Carro")) {
            imagemTipo?.setImageResource(R.drawable.carro)
        } else if (tipo.equals("Moto")) {
            imagemTipo?.setImageResource(R.drawable.moto)
        } else if (tipo.equals("Caminhão")) {
            imagemTipo?.setImageResource(R.drawable.caminhao)
        }
    }
}