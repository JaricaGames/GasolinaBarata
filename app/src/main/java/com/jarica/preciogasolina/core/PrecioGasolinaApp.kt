package com.jarica.preciogasolina.core

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.MainRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class PrecioGasolinaApp : Application() {


}