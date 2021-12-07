package com.deledwards.zipcodefinder

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.deledwards.zipcodefinder.app.ZipCodeViewModel
import com.deledwards.zipcodefinder.databinding.FragmentZipcodesByRadiusStartBinding
import android.content.DialogInterface




/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ZipCodesByRadiusStartFragment : Fragment() {

    private var _binding: FragmentZipcodesByRadiusStartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val zipCodeSharedViewModel: ZipCodeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentZipcodesByRadiusStartBinding.inflate(inflater, container, false)




        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            if(binding.editTextNumber.text.isNullOrBlank()) {
                showAlert("Radius distance must be specified")
            }else{
                val zip = binding.editTextZipCode.text.toString()
                val distance = binding.editTextNumber.text.toString().toInt()
                val bundle = Bundle()
                bundle.putString("zip", zip)
                bundle.putInt("radius", distance)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        zipCodeSharedViewModel.error.removeObservers(viewLifecycleOwner)
    }

    private fun showAlert(message: String) {

        AlertDialog.Builder(context)
            .setTitle("An error occurred 1")
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.ok
            ) { _, _ ->
                // Continue with delete operation
                zipCodeSharedViewModel.clearError()
            } // A null listener allows the button to dismiss the dialog and take no further action.
            //.setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ZipStartFrag"
    }
}