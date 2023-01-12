package com.geardevelopmentbrazil.tabelafipe.models

class Modelo (
    private val nome: String,
    private val codigo: String
) {
    fun retornarNome(): String {
        return this.nome
    }

    fun retornarCodigo(): String {
        return this.codigo
    }
}