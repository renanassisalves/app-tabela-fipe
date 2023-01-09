package com.geardevelopmentbrazil.tabelafipe.models

import android.content.Context
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Utils {
    private val url = "https://parallelum.com.br/fipe/api/v1/"

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    fun exibirErroConexao(context: Context) {
        Toast.makeText(
            context,
            "Erro ao efetuar consulta, tente novamente ou verifique sua internet",
            Toast.LENGTH_SHORT
        ).show()
    }
}