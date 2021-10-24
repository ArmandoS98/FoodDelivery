package com.aesc.fooddelivery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.amountConverter
import com.aesc.fooddelivery.extensions.toast
import com.aesc.fooddelivery.providers.database.models.Pedidos
import com.aesc.fooddelivery.providers.database.viewmodel.ViewModelOrders
import com.aesc.fooddelivery.providers.services.models.Envio
import com.aesc.fooddelivery.providers.services.models.OrderFormatter
import com.aesc.fooddelivery.providers.services.viewmodel.MainViewModel
import com.aesc.fooddelivery.ui.adapters.OrdersAdapter
import com.aesc.fooddelivery.utils.Utils
import com.aesc.fooddelivery.utils.Utils.serializeToJson
import kotlinx.android.synthetic.main.fragment_orders.*


/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment(), View.OnClickListener {
    private lateinit var listOfOrders: List<Pedidos>
    lateinit var viewModalOrder: ViewModelOrders
    private lateinit var adapter: OrdersAdapter
    lateinit var viewModels: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendOrders.setOnClickListener(this)
        getAllOrders()
    }

    private fun getAllOrders() {
        viewModels = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModalOrder = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get()

        viewModalOrder.allorders.observe(viewLifecycleOwner, { orders ->
            if (orders.isEmpty()) {
                tvNoHapPedidos.visibility = View.VISIBLE
                btnSendOrders.isEnabled = false
            } else {
                listOfOrders = orders!!
                recyclerviewInit(orders)
            }
        })
    }

    private fun recyclerviewInit(datos: List<Pedidos>) {
        adapter = OrdersAdapter(requireContext())
        adapter.setCategories(datos)
        rvOrders.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvOrders.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendOrders -> {
                send()
            }
        }
    }

    private fun send() {
        listOfOrders.let { orders ->
            val ordersTemp = mutableListOf<String>()
            var finalAmount = 0.0
            orders.forEach { order ->
                val cant = order.cantidad
                val amount = order.precio
                val price = amountConverter(amount, cant)
                val orderTemp = OrderFormatter(
                    order.id_producto.toString(),
                    order.cantidad,
                    price
                )
                finalAmount += price.toDouble()
                val jsonData: String = serializeToJson(orderTemp)!!
                ordersTemp.add(jsonData)
            }
            val amount = finalAmount
            val sendOrder = Envio(1, ordersTemp.toString(), amount)
            Utils.logsUtils("Order = $sendOrder")

            sendToApi(sendOrder)
        }
    }

    private fun sendToApi(sendOrder: Envio) {
        var status = false
        viewModels.responseStatusSendOrder.observe(viewLifecycleOwner, {
            if (!status) {
                it.let {
                    Utils.logsUtils("SUCCESS $it")
                    viewModalOrder.deleteOrders()
                    viewModalOrder.allorders.observe(viewLifecycleOwner, { orders ->
                        if (orders.isEmpty()) {
                            rvOrders.visibility = View.GONE
                            tvNoHapPedidos.visibility = View.VISIBLE
                            btnSendOrders.isEnabled = false
                        } else {
                            listOfOrders = orders!!
                            recyclerviewInit(orders)
                        }
                    })
                    requireActivity().toast(it.mensaje)
                }
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
            if (it) {
                fragmentProgressBar.visibility = View.VISIBLE
            } else {
                fragmentProgressBar.visibility = View.GONE
            }
        })
        viewModels.sendOrder(sendOrder)
    }
}