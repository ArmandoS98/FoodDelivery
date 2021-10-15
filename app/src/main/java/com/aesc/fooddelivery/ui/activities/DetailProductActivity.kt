package com.aesc.fooddelivery.ui.activities

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.providers.services.viewmodel.MainViewModel
import com.aesc.fooddelivery.ui.adapters.RecomendadosAdapter
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.activity_detail_product.*

class DetailProductActivity : AppCompatActivity() {
    lateinit var viewModels: MainViewModel
    private lateinit var adapter: RecomendadosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        viewModels = ViewModelProvider(this).get(MainViewModel::class.java)
        iniViews()
    }

    private fun iniViews() {
        // setup animation
        val item = intent.extras!!.getParcelable<Producto>("values")
        detail_movie_img.loadByURL(item!!.url_imagen)
        tvNombre.text = item.nombre
        tvAmount.amountConverter(item.precio)
        tvDescripcion.text = item.descripcion
        detail_movie_img.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        logic()
    }

    private fun logic() {
        var status = false
        viewModels.responseProducts.observe(this, {
            if (!status) {
                Utils.logsUtils("SUCCESS $it")
                recyclerviewInit(it.productos)
            }
        })

        viewModels.errorMessage.observe(this, {
            if (!status) {
                Utils.logsUtils("ERROR $it")
            }
        })

        viewModels.loading.observe(this, {
            status = it
            /* if (it) {
                 fragmentProgressBar.visibility = View.VISIBLE
             } else {
                 fragmentProgressBar.visibility = View.GONE
             }*/
        })
        viewModels.products()
    }

    private fun recyclerviewInit(datos: List<Producto>) {
        adapter = RecomendadosAdapter(this, this)
        adapter.setCategories(datos)
        recyclerviewFoods.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerviewFoods.adapter = adapter
    }
}