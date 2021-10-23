package com.aesc.fooddelivery.ui.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.services.models.Dato
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.ui.activities.DetailProductActivity
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.item_categorias_details.view.*


class CategoriasDetailsAdapter(val context: Context?, val requireActivity: FragmentActivity) :
    RecyclerView.Adapter<CategoriasDetailsAdapter.ViewHolder>() {


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
    }

    override fun getItemCount() = datos.size

    fun setCategories(datos: List<Producto>) {
        this.datos = datos
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Le hagamos click a una cardview
            val item: Producto = datos[adapterPosition]
            Utils.logsUtils("Clicked -> $item")

            when (v!!.id) {
                /* R.id.animation_view -> {
                     *//*
                       notifyItemChanged(adapterPosition)*//*
                }*/
                else -> {/*
                    val intent = Intent(context, DetailProductActivity::class.java)
                    intent.putExtra("values", item)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity,
                        itemView.imgCategoria,
                        "sharedName"
                    )
                    context!!.startActivity(intent, options.toBundle())*/

                    //Le hagamos click a una cardview

                    //Animacion
                    val builder = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(R.anim.from_left)
                        .setExitAnim(R.anim.to_right)
                        .setPopEnterAnim(R.anim.from_right)
                        .setPopExitAnim(R.anim.to_left)

                    //Envio de información
                    val bundle = Bundle()
                    bundle.putParcelable(context!!.getString(R.string.key_bundle), item)

                    val opciones: NavOptions = builder.build()

                    Navigation.findNavController(v)
                        .navigate(R.id.nav_producto_fragment, bundle, opciones)
                }
            }

        }
    }
}


