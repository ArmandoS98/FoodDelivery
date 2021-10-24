package com.aesc.fooddelivery.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.backgrounCustom
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.services.models.Dato
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.item_categorias.view.*


class CategoriasAdapter(val context: Context?) :
    RecyclerView.Adapter<CategoriasAdapter.ViewHolder>() {

    private var datos: List<Dato> = listOf()

    fun ViewGroup.inflate(@LayoutRes layoutResID: Int, attachRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutResID, this, attachRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_categorias, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Utils.logsUtils("img ${datos[position].url_imagen}")
        holder.itemView.mcSpace.backgrounCustom(datos[position].nombre!!)
        holder.itemView.imgCategoria.loadByURL(datos[position].url_imagen!!)
        holder.itemView.tvNombre.text = datos[position].nombre
    }

    override fun getItemCount() = datos.size

    fun setCategories(datos: List<Dato>) {
        this.datos = datos
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Le hagamos click a una cardview
            val item: Dato = datos[adapterPosition]
            Utils.logsUtils("Clicked -> $item")

            //Animacion
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.from_left)
                .setExitAnim(R.anim.to_right)
                .setPopEnterAnim(R.anim.from_right)
                .setPopExitAnim(R.anim.to_left)

            //Envio de información
            val bundle = Bundle()
            bundle.putString(context!!.getString(R.string.key_bundle), item.nombre)
            bundle.putString("id_category", item.id.toString())

            val opciones: NavOptions = builder.build()

            Navigation.findNavController(v!!).navigate(R.id.nav_view_categories, bundle, opciones)
        }
    }
}


