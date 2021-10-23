package com.aesc.fooddelivery.providers.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aesc.fooddelivery.providers.database.di.AppDatabase
import com.aesc.fooddelivery.providers.database.models.Pedidos
import com.aesc.fooddelivery.providers.database.repository.OrderRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ViewModelOrders(application: Application) : AndroidViewModel(application) {
    val allorders: LiveData<List<Pedidos>>
    var search: LiveData<List<Pedidos>> = MutableLiveData()
    val repository: OrderRepository

    init {
        val dao = AppDatabase.getAppDatabaseInstance(application).getFavoritesDao()
        repository = OrderRepository(dao)
        allorders = repository.allOrders
    }

    fun search(product_id: Int) {
        search = repository.search(product_id)
    }

    fun deleteOrder(order: Pedidos) = viewModelScope.launch(IO) {
        repository.delete(order)
    }

    fun updateOrder(order: Pedidos) = viewModelScope.launch(IO) {
        repository.update(order)
    }

    fun addOrder(order: Pedidos) = viewModelScope.launch(IO) {
        repository.insert(order)
    }
}