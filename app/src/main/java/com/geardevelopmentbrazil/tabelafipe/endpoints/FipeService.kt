package com.geardevelopmentbrazil.tabelafipe.endpoints

import com.geardevelopmentbrazil.tabelafipe.models.Marca
import com.geardevelopmentbrazil.tabelafipe.models.Veiculo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FipeService {
    @GET("{tipo}/marcas/")
    open fun listarMarcas(@Path("tipo") tipo: String) : Call<List<Marca>>

    @GET("{tipo}/marcas/{codigoMarca}/modelos")
    open fun listarModelos(@Path("tipo") tipo: String,
                           @Path("codigoMarca") codigoMarca: Int) : Call<JsonObject>

    @GET("{tipo}/marcas/{codigoMarca}/modelos/{codigoModelo}/anos")
    open fun listarAnos(@Path("tipo") tipo: String,
                        @Path("codigoMarca") codigoMarca: Int,
                        @Path("codigoModelo") codigoModelo: Int) : Call<Veiculo>
}