package com.geardevelopmentbrazil.tabelafipe.models

class Modelo (
    private val nome: String,
    private val codigo: Int
) {
    fun retornarNome(): String {
        return this.nome
    }

    fun retornarCodigo(): Int {
        return this.codigo
    }
}