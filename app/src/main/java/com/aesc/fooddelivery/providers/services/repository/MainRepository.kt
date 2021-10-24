package com.aesc.fooddelivery.providers.services.repository

import com.aesc.fooddelivery.providers.services.api.MyRetrofitBuilder
import com.aesc.fooddelivery.providers.services.models.Envio

class MainRepository {
    suspend fun categorias() = MyRetrofitBuilder.apiService.categorias()
    suspend fun products() = MyRetrofitBuilder.apiService.products()
    suspend fun sendOrder(order: Envio) = MyRetrofitBuilder.apiService.sendOrders(order)
    suspend fun categorieDetails(idCategoria: String) =
        MyRetrofitBuilder.apiService.categoriasDetails(idCategoria)
}