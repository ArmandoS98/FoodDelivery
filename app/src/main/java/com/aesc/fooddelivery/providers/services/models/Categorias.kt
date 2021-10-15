package com.aesc.fooddelivery.providers.services.models

data class Categorias(
    val respuesta: String? = "",
    val datos: List<Dato>
)

data class Dato(
    val id: Long? = 0,
    val nombre: String? = "",
    val descripcion: String? = "",
    val url_imagen: String? = ""
)
