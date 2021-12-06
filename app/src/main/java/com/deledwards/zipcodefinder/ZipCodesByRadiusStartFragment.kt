package com.deledwards.zipcodefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.deledwards.zipcodefinder.app.ZipCodeViewModel
import com.deledwards.zipcodefinder.databinding.FragmentZipcodesByRadiusStartBinding

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
    ): View? {

        _binding = FragmentZipcodesByRadiusStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            val zip = binding.editTextZipCode.text.toString()
            val distance = binding.editTextNumber.text.toString().toInt()
            zipCodeSharedViewModel.getZipCodesByRadius(zip, distance)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}