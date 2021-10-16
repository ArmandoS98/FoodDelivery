package com.aesc.fooddelivery.providers.database.di

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aesc.fooddelivery.providers.database.models.Favorites

@Dao
interface FavoritesDao {
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
}