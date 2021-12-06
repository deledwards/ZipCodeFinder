package com.deledwards.zipcodefinder.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.domain.ZipCodeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZipCodeViewModel @Inject constructor(private val service: ZipCodeService)

    : ViewModel() {


    private val _zipCodes = MutableLiveData<List<ZipCode>>()
    val zipCodes: LiveData<List<ZipCode>> = _zipCodes


    fun getZipCodesByRadius(zip: String, radius: Int)  {

        viewModelScope.launch {
            val foo = service.getZipCodesWithRadius(zip, radius)
            _zipCodes.value = foo
        }


    }



}