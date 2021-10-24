package com.aesc.fooddelivery.providers.services.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsByCategory(
    val respuesta: String,
    val nombre: String,
    val descripcion: String,
    val productos: List<Productob>
) : Parcelable

@Parcelize
data class Productob(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Long,
    val url_imagen: String
) : Parcelable