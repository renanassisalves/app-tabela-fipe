package com.geardevelopmentbrazil.tabelafipe.models

object Auxiliar {
    private var tipo: String = "carros"
    private var codigoMarca: String = ""
    private var codigoModelo: String = ""
    private var codigoAno: String = ""

    fun setTipo(tipo: String) {
        this.tipo = tipo
    }

    fun setCodigoMarca(codigoMarca: String) {
        this.codigoMarca = codigoMarca
    }

    fun setCodigoModelo(codigoModelo: String) {
        this.codigoModelo = codigoModelo
    }

    fun setCodigoAno(codigoAno: String) {
        this.codigoAno = codigoAno
    }

    fun getTipo(): String {
        return this.tipo
    }

    fun getCodigoMarca(): String {
        return this.codigoMarca
    }

    fun getCodigoModelo(): String {
        return this.codigoModelo
    }

    fun getCodigoAno(): String {
        return this.codigoAno
    }
}