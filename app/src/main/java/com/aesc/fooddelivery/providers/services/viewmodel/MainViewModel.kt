package com.aesc.fooddelivery.providers.services.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aesc.fooddelivery.providers.services.models.*
import com.aesc.fooddelivery.providers.services.repository.MainRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

/**
 * Fuentes
 * https://medium.com/android-news/kotlin-coroutines-and-retrofit-e0702d0b8e8f
 * https://howtodoandroid.com/mvvm-retrofit-recyclerview-kotlin/
 * https://github.com/velmurugan-murugesan/Android-Example/tree/master/MVVMwithKotlinCoroutinesandRetrofit/app
 * https://howtodoandroid.com/mvvm-kotlin-coroutines-retrofit/
 * https://programmerclick.com/article/66871206516/
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage = MutableLiveData<String>()
    val responseCategorias = MutableLiveData<Categorias>()
    val responseCategorieDetails = MutableLiveData<Categorias>()
    val responseProductsByCategory = MutableLiveData<ProductsByCategory>()
    val responseProducts = MutableLiveData<Products>()
    val responseStatusSendOrder = MutableLiveData<StatusEnvio>()
    private var job: CompletableJob? = null
    val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun categorias() {
        loading.value = true
        job = Job()
        job.let { theJob ->
            CoroutineScope(Dispatchers.IO + theJob!! + exceptionHandler).launch {
                try {
                    val response = MainRepository().categorias()
                    withContext(Main) {
                        if (response.isSuccessful) {
                            if (validateResponse(response)) {
                                loading.value = false
                                responseCategorias.postValue(response.body())
                                theJob.complete()
                            } else {
                                val error = "Error login"
                                onError(error)
                            }
                        } else {
                            val msg = response.message()
                            onError(msg)
                        }
                    }
                } catch (t: Throwable) {
                    val msg = t.message.toString()
                    onError(msg)
                }
            }
        }
    }

    fun categorieDetails() {
        loading.value = true
        job = Job()
        job.let { theJob ->
            CoroutineScope(Dispatchers.IO + theJob!! + exceptionHandler).launch {
                try {
                    val response = MainRepository().categorias()
                    withContext(Main) {
                        if (response.isSuccessful) {
                            if (validateResponse(response)) {
                                loading.value = false
                                responseCategorieDetails.postValue(response.body())
                                theJob.complete()
                            } else {
                                val error = "Error login"
                                onError(error)
                            }
                        } else {
                            val msg = response.message()
                            onError(msg)
                        }
                    }
                } catch (t: Throwable) {
                    val msg = t.message.toString()
                    onError(msg)
                }
            }
        }
    }

    fun products() {
        loading.value = true
        job = Job()
        job.let { theJob ->
            CoroutineScope(Dispatchers.IO + theJob!! + exceptionHandler).launch {
                try {
                    val response = MainRepository().products()
                    withContext(Main) {
                        if (response.isSuccessful) {
                            if (validateResponse(response)) {
                                loading.value = false
                                responseProducts.postValue(response.body())
                                theJob.complete()
                            } else {
                                val error = "Error login"
                                onError(error)
                            }
                        } else {
                            val msg = response.message()
                            onError(msg)
                        }
                    }
                } catch (t: Throwable) {
                    val msg = t.message.toString()
                    onError(msg)
                }
            }
        }
    }

    fun productsByCategory(idCategory: String) {
        loading.value = true
        job = Job()
        job.let { theJob ->
            CoroutineScope(Dispatchers.IO + theJob!! + exceptionHandler).launch {
                try {
                    val response = MainRepository().categorieDetails(idCategory)
                    withContext(Main) {
                        if (response.isSuccessful) {
                            if (validateResponse(response)) {
                                loading.value = false
                                responseProductsByCategory.postValue(response.body())
                                theJob.complete()
                            } else {
                                val error = "Error login"
                                onError(error)
                            }
                        } else {
                            val msg = response.message()
                            onError(msg)
                        }
                    }
                } catch (t: Throwable) {
                    val msg = t.message.toString()
                    onError(msg)
                }
            }
        }
    }


    fun sendOrder(order: Envio) {
        loading.value = true
        job = Job()
        job.let { theJob ->
            CoroutineScope(Dispatchers.IO + theJob!! + exceptionHandler).launch {
                try {
                    val response = MainRepository().sendOrder(order)
                    withContext(Main) {
                        if (response.isSuccessful) {
                            if (validateResponse(response)) {
                                loading.value = false
                                responseStatusSendOrder.postValue(response.body())
                                theJob.complete()
                            } else {
                                val error = "Error login"
                                onError(error)
                            }
                        } else {
                            val msg = response.message()
                            onError(msg)
                        }
                    }
                } catch (t: Throwable) {
                    val msg = t.message.toString()
                    onError(msg)
                }
            }
        }
    }

    private fun validateResponse(response: Response<*>): Boolean {
        val json = response.body().toString()
        //{respuesta=ERROR, mensaje=Datos de acceso incorrectos}
        return !json.contains("respuesta=ERROR")
    }

    private fun onError(message: String) {
        try {
            errorMessage.value = message
            loading.value = false
        } catch (ex: Throwable) {
            println("Catching ex in runFailingCoroutine(): $ex")
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
