package com.aesc.fooddelivery.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.loadByURL
import com.aesc.fooddelivery.extensions.toast
import com.aesc.fooddelivery.providers.database.models.Favorites
import com.aesc.fooddelivery.providers.database.models.Pedidos
import com.aesc.fooddelivery.providers.database.viewmodel.MainViewModelFavorites
import com.aesc.fooddelivery.providers.database.viewmodel.ViewModelOrders
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.providers.services.viewmodel.MainViewModel
import com.aesc.fooddelivery.ui.adapters.RecomendadosAdapter
import com.aesc.fooddelivery.ui.interfaces.IOrderNotification
import com.aesc.fooddelivery.utils.Utils
import com.steelkiwi.library.IncrementProductView
import com.steelkiwi.library.listener.OnStateListener
import kotlinx.android.synthetic.main.activity_detail_product.animation_view
import kotlinx.android.synthetic.main.activity_detail_product.detail_movie_img
import kotlinx.android.synthetic.main.activity_detail_product.recyclerviewFoods
import kotlinx.android.synthetic.main.activity_detail_product.tvAmount
import kotlinx.android.synthetic.main.activity_detail_product.tvDescripcion
import kotlinx.android.synthetic.main.activity_detail_product.tvNombre
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.productView
import kotlinx.android.synthetic.main.fragment_product.tvCounter
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.increment_product.*

//MVVM
class ProductFragment : Fragment(), View.OnClickListener {
    private var item: Producto? = null
    private var itemID = 0
    private var interfaceOrders: IOrderNotification? = null
    lateinit var viewModels: MainViewModel
    lateinit var viewModal: MainViewModelFavorites
    lateinit var viewModalOrder: ViewModelOrders
    private lateinit var adapter: RecomendadosAdapter

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
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModels = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get()
        viewModalOrder = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get()
        iniViews()
    }


    private fun iniViews() {
        // setup animation
        itemID = item!!.id.toInt()
        viewModal.search(itemID)
        viewModal.search.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                animation_view.speed = 1f
                animation_view.playAnimation()
            }
        })
        animation_view.setOnClickListener(this)
        btnAddOrder.setOnClickListener(this)
        detail_movie_img.loadByURL(item!!.url_imagen)
        tvNombre.text = item!!.nombre
        tvAmount.amountConverter(item!!.precio)
        tvDescripcion.text = item!!.descripcion
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
        logic()
    }

    private fun logic() {
        var status = false
        viewModels.responseProducts.observe(viewLifecycleOwner, {
            if (!status) {
                Utils.logsUtils("SUCCESS $it")
                recyclerviewInit(it.productos)
            }
        })

        viewModels.errorMessage.observe(viewLifecycleOwner, {
            if (!status) {
                Utils.logsUtils("ERROR $it")
                requireActivity().toast(getString(R.string.msg_algo_salio_mal))
            }
        })

        viewModels.loading.observe(viewLifecycleOwner, {
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
        adapter = RecomendadosAdapter(requireContext(), requireActivity())
        adapter.setCategories(datos)
        recyclerviewFoods.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerviewFoods.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.animation_view -> {
                var status = false
                viewModal.search(itemID)
                viewModal.search.observe(this, { list ->
                    if (list.isEmpty()) {
                        if (!status) {
                            v.animation_view.speed = 1f
                            v.animation_view.playAnimation()
                            viewModal.addFavorite(
                                Favorites(
                                    id_product = item!!.id.toInt(),
                                    nombre_product = item!!.nombre,
                                    descripcion_product = item!!.descripcion,
                                    precio_product = item!!.precio,
                                    url_imagen_product = item!!.url_imagen
                                )
                            )
                            Utils.logsUtils("Agregado")
                            Toast.makeText(
                                requireContext(),
                                "Agregado a Favoritos",
                                Toast.LENGTH_SHORT
                            ).show()
                            status = true
                        }
                    } else {
                        if (!status) {
                            v.animation_view.speed = 0f
                            v.animation_view.playAnimation()
                            viewModal.deleteFavorite(list[0])
                            list.forEach {
                                Utils.logsUtils("Removido")
                                Toast.makeText(
                                    requireContext(),
                                    "Se Removio de Favoritos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            status = true
                        }
                    }
                })
            }
            R.id.btnAddOrder -> {
                //Agregar logica para agregar a pedidos
                viewModalOrder.addOrder(
                    Pedidos(
                        id_producto = item!!.id,
                        nombre_producto = item!!.nombre,
                        descripcion_producto = item!!.descripcion,
                        precio = item!!.precio.toString(),
                        url_imagen_producto = item!!.url_imagen,
                        id_table_producto = item!!.id_table,
                        cantidad = tvCounter.text.toString(),
                    )
                )
                interfaceOrders!!.addOtherOrder()
                requireActivity().toast(getString(R.string.msg_add_order_successful))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interfaceOrders = context as IOrderNotification
    }
}

