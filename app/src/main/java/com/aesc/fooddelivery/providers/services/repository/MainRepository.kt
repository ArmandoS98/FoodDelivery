package com.aesc.fooddelivery.providers.services.repository

import com.aesc.fooddelivery.providers.services.api.MyRetrofitBuilder

class MainRepository {
    suspend fun categorias() = MyRetrofitBuilder.apiService.categorias()
    suspend fun products() = MyRetrofitBuilder.apiService.products()
    suspend fun categorieDetails(idCategoria: String) =
        MyRetrofitBuilder.apiService.categoriasDetails(idCategoria)
}