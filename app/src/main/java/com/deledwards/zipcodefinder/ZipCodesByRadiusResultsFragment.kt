package com.deledwards.zipcodefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deledwards.zipcodefinder.app.ZipCodeViewModel
import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.databinding.FragmentItemBinding
import com.deledwards.zipcodefinder.databinding.FragmentItemListBinding
import com.deledwards.zipcodefinder.databinding.FragmentZipcodesByRadiusResultsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ZipCodesByRadiusResultsFragment : Fragment() {

    private lateinit var spinner: ProgressBar
    private lateinit var myadapter: ZipCodeRecyclerViewAdapter
    private var columnCount = 1

    private val zipCodeSharedViewModel: ZipCodeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list)
        spinner = view.findViewById(R.id.spinner)

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                myadapter = ZipCodeRecyclerViewAdapter(listOf())
                adapter = myadapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zipCodeSharedViewModel.zipCodes.observe(viewLifecycleOwner, {
            myadapter.update(it)
        })

        zipCodeSharedViewModel.loading.observe(viewLifecycleOwner, { visible ->
            when {
                visible -> {
                    spinner.visibility = View.VISIBLE
                }
                else -> {
                    spinner.visibility = View.GONE
                }
            }
        })
    }
}