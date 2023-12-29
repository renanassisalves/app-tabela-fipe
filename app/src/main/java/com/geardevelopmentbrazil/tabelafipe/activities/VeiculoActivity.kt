package com.geardevelopmentbrazil.tabelafipe.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Auxiliar
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import com.geardevelopmentbrazil.tabelafipe.models.Veiculo
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VeiculoActivity : AppCompatActivity() {

    var imagemTipo: ImageView? = null

    var nomeVeiculo: TextView? = null
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

    var veiculo: Veiculo? = null

    var btnConsultar: Button? = null
    var btnCopiar: Button? = null
    var btnCompartilhar: Button? = null

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculo)

        val bannerSuperior: AdView = findViewById(R.id.bannerVeiculoSuperior)
        val bannerInferior: AdView = findViewById(R.id.bannerVeiculoInferior)
        val adRequest = AdRequest.Builder().build()
        bannerSuperior.loadAd(adRequest)
        bannerInferior.loadAd(adRequest)

        InterstitialAd.load(this, getString(R.string.interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.");
                Handler().postDelayed({
                    mInterstitialAd = interstitialAd;
                    mInterstitialAd?.show(this@VeiculoActivity);
                }, 5000)

            }
        })

        imagemTipo = findViewById(R.id.imagemTipoVeiculo)
        nomeVeiculo = findViewById(R.id.nomeVeiculo)
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

        btnConsultar = findViewById(R.id.btnConsultarNovamente)
        btnCopiar = findViewById(R.id.btnCopiar)
        btnCompartilhar = findViewById(R.id.btnCompartilhar)

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

                    nomeVeiculo?.setText(veiculo?.Marca + " " + veiculo?.Modelo)
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

        btnConsultar!!.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCopiar!!.setOnClickListener{
            copiarTexto()
        }

        btnCompartilhar!!.setOnClickListener{
            compartilharTexto()
        }
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

    fun copiarTexto() {
        var texto = "*Consulta tabela Fipe do veículo: \n" + veiculo?.Marca + " " + veiculo?.Modelo + " Ano " +
                veiculo?.AnoModelo +
                "*\n Tipo: " + veiculo?.TipoVeiculo +
                "\n Marca: " + veiculo?.Marca +
                "\n Modelo: " + veiculo?.Modelo +
                "\n Ano: " + veiculo?.AnoModelo
                "\n Combustível: " + veiculo?.Combustivel +
                "\n Sigla Combustível: " + veiculo?.SiglaCombustivel
                "\n Código Fipe: " + veiculo?.CodigoFipe +
                "\n Valor: " + veiculo?.Valor +
                "\n Data de Referência: " + veiculo?.MesReferencia
        var textoCompartilhamento = "\n\nTabela Fipe consultada pelo aplicativo:\nhttps://play.google.com/store/apps/details?id=com.geardevelopmentbrazil.tabelafipe"

        var textoCopia = texto+textoCompartilhamento

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copia = ClipData.newPlainText("tabela_fipe", textoCopia)
        clipboard.setPrimaryClip(copia)
        val toast = Toast.makeText(applicationContext, "Texto copiado!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun compartilharTexto() {
        var texto = "*Consulta tabela Fipe do veículo: \n" + veiculo?.Marca + " " + veiculo?.Modelo + " Ano " +
                veiculo?.AnoModelo +
                "*\n Tipo: " + veiculo?.TipoVeiculo +
                "\n Marca: " + veiculo?.Marca +
                "\n Modelo: " + veiculo?.Modelo +
                "\n Ano: " + veiculo?.AnoModelo +
                "\n Combustível: " + veiculo?.Combustivel +
                "\n Sigla Combustível: " + veiculo?.SiglaCombustivel
                "\n Código Fipe: " + veiculo?.CodigoFipe +
                "\n Valor: " + veiculo?.Valor +
                "\n Data de Referência: " + veiculo?.MesReferencia
        var textoCompartilhamento = "\n\nTabela Fipe consultada pelo aplicativo:\nhttps://play.google.com/store/apps/details?id=com.geardevelopmentbrazil.tabelafipe"

        var textoCopia = texto+textoCompartilhamento

        var intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, textoCopia)
        intent.setType("text/plain")

        startActivity(intent)
    }
}