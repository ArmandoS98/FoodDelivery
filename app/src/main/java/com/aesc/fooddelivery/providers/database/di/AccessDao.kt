package com.aesc.fooddelivery.providers.database.di

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aesc.fooddelivery.providers.database.models.Favorites
import com.aesc.fooddelivery.providers.database.models.Pedidos

@Dao
interface AccessDao {
    @Query("SELECT * FROM favorite_entity")
    fun getAllFavorites(): LiveData<List<Favorites>>

    @Query("SELECT * FROM favorite_entity WHERE id_product = :product_id")
    fun getFavorites(product_id: Int): LiveData<List<Favorites>>

    @Insert
    fun insertFavorite(favorite: Favorites)

    @Update
    fun updateFavorite(favorite: Favorites)

    @Delete
    fun deleteFavorite(favorite: Favorites)

    //Orders
    @Query("SELECT * FROM order_entity")
    fun getAllOrders(): LiveData<List<Pedidos>>

    @Query("SELECT * FROM order_entity WHERE id_producto = :product_id")
    fun getOrders(product_id: Int): LiveData<List<Pedidos>>

    @Insert
    fun insertOrders(order: Pedidos)

    @Update
    fun updateOrders(order: Pedidos)

    @Delete
    fun deleteOrders(order: Pedidos)
}