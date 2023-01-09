package com.geardevelopmentbrazil.tabelafipe.models

import java.io.Serializable

class Marca (
    private val nome: String,
    private val codigo: Int


) : Serializable {
    fun retornarMarca(): String{
        return this.nome
    }

    fun retornarCodigo(): Int {
        return this.codigo
    }
}