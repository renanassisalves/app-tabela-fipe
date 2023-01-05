package com.geardevelopmentbrazil.tabelafipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.geardevelopmentbrazil.tabelafipe.R
import com.geardevelopmentbrazil.tabelafipe.endpoints.FipeService
import com.geardevelopmentbrazil.tabelafipe.models.Marca
import com.geardevelopmentbrazil.tabelafipe.models.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById<Spinner>(R.id.spinnerTipo)
        val btnConsultar: Button = findViewById(R.id.btnConsultar)
        val retrofit: Retrofit = Utils().getRetrofitInstance()
        val service = retrofit.create(FipeService::class.java)
        var tipoSelecionado: String = "carros"

        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_veiculo,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoSelecionado = when(position) {
                    0 -> "carros"
                    1 -> "motos"
                    2 -> "caminhoes"
                    else -> "carros"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        btnConsultar.setOnClickListener {
            val callback = service.listarMarcas(tipoSelecionado)

            callback.enqueue(object : Callback<List<Marca>> {
                override fun onFailure(call: Call<List<Marca>>, t: Throwable) {
                    Toast.makeText(
                        baseContext,
                        "Erro ao efetuar consulta, tente novamente ou verifique sua internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<List<Marca>>, response: Response<List<Marca>>) {
                    val intent = Intent(applicationContext, ListagemMarca::class.java)
                    intent.putExtra("MARCAS", response.body() as Serializable)
                    startActivity(intent)
                }
            })
        }
    }
}