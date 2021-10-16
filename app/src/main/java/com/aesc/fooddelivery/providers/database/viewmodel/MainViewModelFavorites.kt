package com.aesc.fooddelivery.providers.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aesc.fooddelivery.providers.database.di.AppDatabase
import com.aesc.fooddelivery.providers.database.repository.FavoriteRepository
import com.aesc.fooddelivery.providers.database.models.Favorites
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModelFavorites(application: Application) : AndroidViewModel(application) {
    val allFanovites: LiveData<List<Favorites>>
    var search: LiveData<List<Favorites>> = MutableLiveData()
    val repository: FavoriteRepository

    init {
        val dao = AppDatabase.getAppDatabaseInstance(application).getFavoritesDao()
        repository = FavoriteRepository(dao)
        allFanovites = repository.allFavorites
    }

    fun search(product_id: Int) {
        search = repository.search(product_id)
    }

    fun deleteFavorite(favorite: Favorites) = viewModelScope.launch(IO) {
        repository.delete(favorite)
    }

    fun updateFavorite(favorite: Favorites) = viewModelScope.launch(IO) {
        repository.update(favorite)
    }

    fun addFavorite(favorite: Favorites) = viewModelScope.launch(IO) {
        repository.insert(favorite)
    }
}