package com.aesc.fooddelivery.ui.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.database.models.Favorites
import com.aesc.fooddelivery.providers.database.viewmodel.MainViewModelFavorites
import com.aesc.fooddelivery.providers.services.models.Dato
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.ui.activities.DetailProductActivity
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.item_categorias_details.view.*
import kotlinx.android.synthetic.main.item_food.view.*


class CategoriasDetailsAdapter(val context: Context?, val requireActivity: FragmentActivity) :
    RecyclerView.Adapter<CategoriasDetailsAdapter.ViewHolder>() {
    lateinit var viewModal: MainViewModelFavorites

    private var datos: List<Producto> = listOf()

    fun ViewGroup.inflate(@LayoutRes layoutResID: Int, attachRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutResID, this, attachRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_categorias_details, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.imgCategoria.loadByURL(datos[position].url_imagen)
        holder.itemView.tvNombre.text = datos[position].nombre
        holder.itemView.tvDesc.text = datos[position].descripcion
        holder.itemView.tvAmount.amountConverter(datos[position].precio)

        viewModal.search(datos[position].id.toInt())
        viewModal.search.observe(requireActivity, { list ->
            if (list.isNotEmpty()) {
                holder.itemView.animation_view.speed = 1f
                holder.itemView.animation_view.playAnimation()
            } else {
                holder.itemView.animation_view.speed = 0f
                holder.itemView.animation_view.playAnimation()
            }
        })
    }

    override fun getItemCount() = datos.size

    fun setCategories(datos: List<Producto>) {
        this.datos = datos
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        init {
            viewModal = ViewModelProvider(
                requireActivity,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity.application)
            ).get()
            v.setOnClickListener(this)
            v.animation_view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Le hagamos click a una cardview
            val item: Producto = datos[adapterPosition]
            Utils.logsUtils("Clicked -> $item")

            when (v!!.id) {
                R.id.animation_view -> {
                    var status = false
                    viewModal.search(item.id.toInt())
                    viewModal.search.observe(requireActivity, { list ->
                        if (list.isEmpty()) {
                            if (!status) {
                                v.animation_view.speed = 1f
                                v.animation_view.playAnimation()
                                viewModal.addFavorite(
                                    Favorites(
                                        id_product = item.id.toInt(),
                                        nombre_product = item.nombre,
                                        descripcion_product = item.descripcion,
                                        precio_product = item.precio,
                                        url_imagen_product = item.url_imagen
                                    )
                                )
                                Utils.logsUtils("Agregado")
                                status = true
                            }
                        } else {
                            if (!status) {
                                v.animation_view.speed = 0f
                                v.animation_view.playAnimation()
                                viewModal.deleteFavorite(list[0])
                                list.forEach {
                                    Utils.logsUtils("Removido")
                                }
                                status = true
                            }
                        }
                    })
                }
                else -> {
                    // here we send movie information to detail activity
                    // also we ll create the transition animation between the two activity
                    val intent = Intent(context, DetailProductActivity::class.java)
                    // send movie information to deatilActivity
                    intent.putExtra("values", item)
                    // lets crezte the animation
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity,
                        itemView.imgCategoria,
                        "sharedName"
                    )
                    context!!.startActivity(intent, options.toBundle())
                }
            }

        }
    }
}


