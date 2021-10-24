package com.aesc.fooddelivery.providers.services.models

data class Envio(
    val id_cliente: Int? = 0,
    val json_pedido: String? = "",
    val total: Double? = 0.0,
)