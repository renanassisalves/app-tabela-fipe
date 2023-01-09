package com.geardevelopmentbrazil.tabelafipe.models

class Veiculo (
    val valor: String,
    val marca: Marca,
    val modelo: Modelo,
    val anoModelo: Ano,
    val combustivel: String,
    val codigoFipe: String,
    val mesReferencia: String,
    val tipoVeiculo: Int,
    val siglaCombustivel: String
)