package com.jarica.preciogasolina.ui.ui.SplashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor() :
    ViewModel() {

    private val _progressIndicator = MutableLiveData<Float>()
    val progressIndicator: LiveData<Float> = _progressIndicator


    init {
        _progressIndicator.value = 0f
    }

    fun progressIndicator() {
        CoroutineScope(Dispatchers.Main).launch {
            if (_progressIndicator.value!! < 1f) {
                _progressIndicator.value = _progressIndicator.value?.plus(0.005f)
            }

            withContext(Dispatchers.IO) {
                Thread.sleep(50)
            }



        }
    }


}