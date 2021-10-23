package com.aesc.fooddelivery.providers.database.repository

import androidx.lifecycle.LiveData
import com.aesc.fooddelivery.providers.database.di.AccessDao
import com.aesc.fooddelivery.providers.database.models.Pedidos

class OrderRepository(private val orderDao: AccessDao) {

    val allOrders: LiveData<List<Pedidos>> = orderDao.getAllOrders()

    fun search(product_id: Int) = orderDao.getOrders(product_id)

    suspend fun insert(order: Pedidos) {
        orderDao.insertOrders(order)
    }

    suspend fun delete(order: Pedidos) {
        orderDao.deleteOrders(order)
    }

    suspend fun update(order: Pedidos) {
        orderDao.updateOrders(order)
    }
}