package com.aesc.fooddelivery.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.amountConverterString
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.providers.database.models.Pedidos
import com.steelkiwi.library.listener.OnStateListener
import kotlinx.android.synthetic.main.fragment_preview_orderes.*

/**
 * A simple [Fragment] subclass.
 * Use the [PreviewOrderesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreviewOrderesFragment : Fragment() {
    private var item: Pedidos? = null
    private var itemID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable("key_titulo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_orderes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail_movie_img.loadByURL(item!!.url_imagen_producto)
        tvNombre.text = item!!.nombre_producto
        tvAmount.amountConverterString(item!!.precio, item!!.cantidad)
        tvDescripcion.text = item!!.descripcion_producto
        tvCounter.text = item!!.cantidad
        detail_movie_img.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_animation)
//        productView.setParentMiddleIcon(resources.getDrawable(R.drawable.des))
        productView.defaultBackgroundColor = resources.getColor(R.color.purple_500)
        productView.setOnStateListener(object : OnStateListener {
            override fun onCountChange(count: Int) {
                tvCounter.text = if (count == 0) {
                    "1"
                } else {
                    "$count"
                }
            }

            override fun onConfirm(count: Int) {

            }

            override fun onClose() {

            }

        })
    }

}