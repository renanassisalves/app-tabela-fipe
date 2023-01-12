package com.geardevelopmentbrazil.tabelafipe.models

import java.io.Serializable

class Marca (
    private val nome: String,
    private val codigo: String
) : Serializable {
    fun retornarMarca(): String{
        return this.nome
    }

    fun retornarCodigo(): String {
        return this.codigo
    }
}