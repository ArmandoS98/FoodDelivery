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
import com.aesc.fooddelivery.providers.database.models.Pedidos
import com.aesc.fooddelivery.providers.database.viewmodel.ViewModelOrders
import com.aesc.fooddelivery.ui.adapters.OrdersAdapter
import kotlinx.android.synthetic.main.fragment_orders.*


/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    lateinit var viewModalOrder: ViewModelOrders
    private lateinit var adapter: OrdersAdapter

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
        getAllOrders()
    }

    private fun getAllOrders() {
        viewModalOrder = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get()

        viewModalOrder.allorders.observe(viewLifecycleOwner, { orders ->
            if (orders.isEmpty()) {
                tvNoHapPedidos.visibility = View.VISIBLE
                btnSendOrders.isEnabled = false
            } else {
                recyclerviewInit(orders!!)
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
}