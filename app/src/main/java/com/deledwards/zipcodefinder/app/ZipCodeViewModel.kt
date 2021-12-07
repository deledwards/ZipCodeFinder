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
import android.R
import android.content.res.Resources
import java.io.InputStream


@HiltViewModel
class ZipCodeViewModel @Inject constructor(private val service: ZipCodeService)
    : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _zipCodes = MutableLiveData<List<ZipCode>>()
    val zipCodes: LiveData<List<ZipCode>> = _zipCodes

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun getZipCodesByRadius(zip: String, radius: Int)  {

        viewModelScope.launch {
            try {
                _loading.value = true
                _zipCodes.value = listOf()

                val foo = service.getZipCodesWithRadius(zip, radius)
                _zipCodes.value = foo
            }catch (ex: Exception){
                Log.e("ViewModel", ex.message.toString())
                _error.value = ex
            }finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }


}