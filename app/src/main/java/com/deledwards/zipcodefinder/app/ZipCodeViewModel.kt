package com.deledwards.zipcodefinder.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.domain.model.ZipCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ZipCodeViewModel @Inject constructor(private val service: ZipCodeService)
    : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _zipCodes = MutableLiveData<List<ZipCode>>()
    val zipCodes: LiveData<List<ZipCode>> = _zipCodes

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun getZipCodesByRadius(zip: String, radius: Int) {

        viewModelScope.launch {
            try {
                _loading.value = true
                _zipCodes.value = listOf()

                when (val zipRet = service.getZipCodesWithRadius2(zip, radius)) {
                    is Either.Left ->  {
                        _zipCodes.value = listOf()
                        _error.value = zipRet.value
                    }
                    is Either.Right -> {
                        _zipCodes.value  = zipRet.value
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, ex.message.toString())
                _error.value = ex
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    companion object {
        const val TAG = "ZipCodeVModel"
    }

}