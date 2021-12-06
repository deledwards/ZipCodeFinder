package com.deledwards.zipcodefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deledwards.zipcodefinder.app.ZipCodeViewModel
import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.databinding.FragmentZipcodesByRadiusResultsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ZipCodesByRadiusResultsFragment : Fragment() {

    private lateinit var myadapter: ZipCodeRecyclerViewAdapter
    private var columnCount = 1
    private var _binding: FragmentZipcodesByRadiusResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    private val zipCodeSharedViewModel: ZipCodeViewModel by activityViewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentZipcodesByRadiusResultsBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val view = inflater.inflate(R.layout.fragment_item_list, container, false)

    // Set the adapter
    if (view is RecyclerView) {
        with(view) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = ZipCodeRecyclerViewAdapter(listOf<ZipCode>())//PlaceholderContent.ITEMS)
            myadapter = adapter as ZipCodeRecyclerViewAdapter
        }
    }
    return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zipCodeSharedViewModel.zipCodes.observe( viewLifecycleOwner,  Observer {
            myadapter.update(it)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}