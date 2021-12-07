package com.deledwards.zipcodefinder

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deledwards.zipcodefinder.app.ZipCodeViewModel

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

        zipCodeSharedViewModel.error.observe(viewLifecycleOwner, {
            if(it == null) return@observe

            Log.i(ZipCodesByRadiusStartFragment.TAG, it.message.toString())
            showAlert("${it.message}")
        })

        val zip = arguments?.getString("zip")
        val distance = arguments?.getInt("radius")
        if (zip != null) {
            if (distance != null) {
                zipCodeSharedViewModel.getZipCodesByRadius(zip, distance)
            }
        }
    }

    private fun showAlert(message: String) {

        AlertDialog.Builder(context)
            .setTitle("An error occurred")
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.ok
            ) { _, _ ->
                // Continue with delete operation
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                zipCodeSharedViewModel.clearError()
            } // A null listener allows the button to dismiss the dialog and take no further action.
            //.setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    companion object {
        const val TAG = "ZipResultsFrag"
    }
}