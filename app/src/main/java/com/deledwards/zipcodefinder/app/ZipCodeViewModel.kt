package com.deledwards.zipcodefinder.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deledwards.zipcodefinder.domain.ZipCodeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZipCodeViewModel @Inject constructor(private val service: ZipCodeService)

    : ViewModel() {


    fun test(zip: String, radius: Int)  {

        viewModelScope.launch {
            val foo = service.getZipCodesWithRadius(zip, radius)

            Log.e("ZipCodeViewModel", foo.toString())
        }


    }



}