package com.aesc.fooddelivery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.toast
import com.aesc.fooddelivery.providers.database.viewmodel.MainViewModelFavorites
import com.aesc.fooddelivery.providers.services.models.Producto
import com.aesc.fooddelivery.providers.services.models.Productob
import com.aesc.fooddelivery.providers.services.viewmodel.MainViewModel
import com.aesc.fooddelivery.ui.activities.MainActivity
import com.aesc.fooddelivery.ui.adapters.CategoriasDetailsAdapter
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.fragment_view_categorie.*

/**
 * A simple [Fragment] subclass.
 * Use the [ViewCategorieFragment] factory method to
 * create an instance of this fragment.
 */
class ViewCategorieFragment : Fragment() {
    lateinit var viewModels: MainViewModel
    private lateinit var adapter: CategoriasDetailsAdapter
    private var titulo_fragment: String? = ""
    private var id_category: String? = ""
    lateinit var viewModal: MainViewModelFavorites


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titulo_fragment = it.getString(getString(R.string.key_bundle))
            id_category = it.getString("id_category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_categorie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModels = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get()
        (activity as MainActivity).supportActionBar?.title = titulo_fragment
        logic()
    }


    private fun logic() {
        var status = false
        viewModels.responseProductsByCategory.observe(viewLifecycleOwner, {
            if (!status) {
                Utils.logsUtils("SUCCESS $it")
                it.productos.let { products ->
                    recyclerviewInit(products)
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
        viewModels.productsByCategory(id_category!!)
    }

    private fun recyclerviewInit(datos: List<Productob>) {
        adapter = CategoriasDetailsAdapter(requireContext(), requireActivity())
        adapter.setCategories(datos)
        recyclerview.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerview.adapter = adapter
    }
}