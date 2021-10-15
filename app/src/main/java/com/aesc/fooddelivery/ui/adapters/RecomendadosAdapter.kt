package com.aesc.fooddelivery.ui.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.ui.activities.DetailProductActivity
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.item_food.view.*


class RecomendadosAdapter(val context: Context?, val requireActivity: FragmentActivity) :
    RecyclerView.Adapter<RecomendadosAdapter.ViewHolder>() {

    private var datos: List<Producto> = listOf()

    fun ViewGroup.inflate(@LayoutRes layoutResID: Int, attachRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutResID, this, attachRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_food, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_movie_img.loadByURL(datos[position].url_imagen)
        holder.itemView.item_movie_title.text = datos[position].nombre
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


            // here we send movie information to detail activity
            // also we ll create the transition animation between the two activity
            val intent = Intent(context, DetailProductActivity::class.java)
            // send movie information to deatilActivity
            intent.putExtra("values", item)
            // lets crezte the animation
            val options = ActivityOptions.makeSceneTransitionAnimation(
                requireActivity,
                itemView.item_movie_img,
                "sharedName"
            )

            context!!.startActivity(intent, options.toBundle())
        }
    }
}


