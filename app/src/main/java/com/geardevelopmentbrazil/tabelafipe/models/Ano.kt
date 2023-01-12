package com.geardevelopmentbrazil.tabelafipe.models

class Ano (
    private val ano: String,
    private val codigo: String
) {
    fun retornarAno(): String {
        if (ano == null) {
            return this.codigo
        } else {
            return this.ano+"\n\n"+this.codigo
        }
    }

    fun retornarCodigo(): String {
        return this.codigo
    }
}