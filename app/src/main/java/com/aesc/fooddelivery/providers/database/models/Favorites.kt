package com.aesc.fooddelivery.providers.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_entity")
class Favorites(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "id_product") val id_product: Int? = 0,
    @ColumnInfo(name = "nombre_product") val nombre_product: String,
    @ColumnInfo(name = "descripcion_product") val descripcion_product: String,
    @ColumnInfo(name = "precio_product") val precio_product: Long,
    @ColumnInfo(name = "url_imagen_product") val url_imagen_product: String
)