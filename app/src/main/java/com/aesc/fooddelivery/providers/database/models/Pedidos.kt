package com.aesc.fooddelivery.providers.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "order_entity")
class Pedidos(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "id_producto") val id_producto: Long,
    @ColumnInfo(name = "nombre_producto") val nombre_producto: String,
    @ColumnInfo(name = "descripcion_producto") val descripcion_producto: String,
    @ColumnInfo(name = "precio") val precio: String,
    @ColumnInfo(name = "url_imagen_producto") val url_imagen_producto: String,
    @ColumnInfo(name = "id_table_producto") val id_table_producto: Long? = 1,
    @ColumnInfo(name = "cantidad") val cantidad: String,
) : Parcelable