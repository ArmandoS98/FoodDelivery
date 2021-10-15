package com.aesc.fooddelivery.providers.services.api

import com.aesc.fooddelivery.providers.services.models.Categorias
import com.aesc.fooddelivery.providers.services.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    //CATERGORIAS
    @GET("api/categorias")
    suspend fun categorias(): Response<Categorias>

    //CATERGORIAS
    @GET("api/categorías/{idCategoria}")
    suspend fun categoriasDetails(@Path("idCategoria") idCategoria: String): Response<Categorias>

    //PRODUCTOS
    @GET("api/productos")
    suspend fun products(): Response<Products>
}