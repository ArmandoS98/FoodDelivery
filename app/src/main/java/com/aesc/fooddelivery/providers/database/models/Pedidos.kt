package com.aesc.fooddelivery.providers.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_entity")
class Pedidos(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "id_producto") val id_producto: String,
    @ColumnInfo(name = "cantidad") val cantidad: String,
    @ColumnInfo(name = "precio") val precio: String,
)