package com.aesc.fooddelivery.providers.services.api

import com.aesc.fooddelivery.providers.services.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    //CATERGORIAS
    @GET("api/categorias")
    suspend fun categorias(): Response<Categorias>

    //CATERGORIAS
    @GET("api/categorias/{idCategoria}")
    suspend fun categoriasDetails(@Path("idCategoria") idCategoria: String): Response<ProductsByCategory>

    //PRODUCTOS
    @GET("api/productos")
    suspend fun products(): Response<Products>

    //ENVIAR PEDIDOS
    @POST("/api/pedidos")
    suspend fun sendOrders(@Body root: Envio?): Response<StatusEnvio>
}