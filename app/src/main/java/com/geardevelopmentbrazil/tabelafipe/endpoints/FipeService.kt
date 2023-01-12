package com.geardevelopmentbrazil.tabelafipe.endpoints

import com.geardevelopmentbrazil.tabelafipe.models.Marca
import com.geardevelopmentbrazil.tabelafipe.models.Veiculo
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FipeService {
    @GET("{tipo}/marcas")
    open fun getMarcas(@Path("tipo") tipo: String) : Call<List<Marca>>

    @GET("{tipo}/marcas/{codigoMarca}/modelos")
    open fun getModelos(@Path("tipo") tipo: String,
                        @Path("codigoMarca") codigoMarca: String
    ) : Call<JsonObject>

    @GET("{tipo}/marcas/{codigoMarca}/modelos/{codigoModelo}/anos")
    open fun getAnos(@Path("tipo") tipo: String,
                     @Path("codigoMarca") codigoMarca: String,
                     @Path("codigoModelo") codigoModelo: String
    ) : Call<JsonArray>

    @GET("{tipo}/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{codigoAno}")
    open fun getVeiculo(@Path("tipo") tipo: String,
                     @Path("codigoMarca") codigoMarca: String,
                     @Path("codigoModelo") codigoModelo: String,
                        @Path("codigoAno") codigoAno: String
    ) : Call<JsonObject>
}