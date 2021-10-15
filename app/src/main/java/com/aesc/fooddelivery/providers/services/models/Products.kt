package com.aesc.fooddelivery.providers.services.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Products(
    val respuesta: String,
    val productos: List<Producto>
) : Parcelable

@Parcelize
data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Long,
    val url_imagen: String
) : Parcelable

