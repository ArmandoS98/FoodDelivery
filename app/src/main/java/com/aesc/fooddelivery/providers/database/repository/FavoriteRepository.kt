package com.aesc.fooddelivery.providers.database.repository

import androidx.lifecycle.LiveData
import com.aesc.fooddelivery.providers.database.models.Favorites
import com.aesc.fooddelivery.providers.database.di.AccessDao

class FavoriteRepository(private val favoriteDao: AccessDao) {

    val allFavorites: LiveData<List<Favorites>> = favoriteDao.getAllFavorites()

    fun search(product_id: Int) = favoriteDao.getFavorites(product_id)

    suspend fun insert(favorite: Favorites) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun delete(favorite: Favorites) {
        favoriteDao.deleteFavorite(favorite)
    }

    suspend fun update(favorite: Favorites) {
        favoriteDao.updateFavorite(favorite)
    }
}