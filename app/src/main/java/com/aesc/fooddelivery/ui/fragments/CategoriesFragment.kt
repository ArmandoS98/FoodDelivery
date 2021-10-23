package com.aesc.fooddelivery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aesc.fooddelivery.R
import com.aesc.fooddelivery.extensions.toast
import com.aesc.fooddelivery.providers.services.models.Dato
import com.aesc.fooddelivery.providers.services.viewmodel.MainViewModel
import com.aesc.fooddelivery.ui.adapters.CategoriasAdapter
import com.aesc.fooddelivery.utils.Utils
import kotlinx.android.synthetic.main.fragment_categories.*

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    lateinit var viewModels: MainViewModel
    private lateinit var adapter: CategoriasAdapter

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
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModels = ViewModelProvider(this).get(MainViewModel::class.java)
        logic()
    }

    private fun logic() {
        var status = false
        viewModels.responseCategorias.observe(viewLifecycleOwner, {
            if (!status) {
                Utils.logsUtils("SUCCESS $it")
                recyclerviewInit(it.datos)
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
        viewModels.categorias()
    }

    private fun recyclerviewInit(datos: List<Dato>) {
        adapter = CategoriasAdapter(requireContext())
        adapter.setCategories(datos)
        recyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = adapter
    }
}