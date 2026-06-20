package com.jarica.preciogasolina.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {


    suspend fun savePreviewSearch(
        gasoline: String,
        province: String,
        town: String,
        idGasolinaSeleccionada: String,
        idProvinciaSeleccionada: String,
        idMunicipioSeleccionado: String,
        nameGasolinaSeleccionada: String,
    ) {
        context.dataStore.edit { preference ->
            preference[stringPreferencesKey(GASOLINE_KEY)] = gasoline
            preference[stringPreferencesKey(PROVINCE_KEY)] = province
            preference[stringPreferencesKey(TOWN_KEY)] = town
            preference[stringPreferencesKey(ID_PROVINCE_KEY)] = idProvinciaSeleccionada
            preference[stringPreferencesKey(ID_GASOLINE_SELECTED)] = idGasolinaSeleccionada
            preference[stringPreferencesKey(ID_TOWN_SELECTED)] = idMunicipioSeleccionado
            preference[stringPreferencesKey(NAME_GASOLINE_SELECTED)] = nameGasolinaSeleccionada

        }
    }

    fun loadPreviewSearch(): Flow<PreviewSearch> {
         return context.dataStore.data.map { preference ->
            PreviewSearch(
                gasoline = preference [stringPreferencesKey(GASOLINE_KEY)] ?: "",
                province = preference [stringPreferencesKey(PROVINCE_KEY)] ?: "",
                town = preference [stringPreferencesKey(TOWN_KEY)] ?: "",
                idGasolinaSeleccionada = preference [stringPreferencesKey(ID_GASOLINE_SELECTED)] ?: "",
                idProvinciaSeleccionada = preference [stringPreferencesKey(ID_PROVINCE_KEY)] ?: "",
                idMunicipioSeleccionado = preference [stringPreferencesKey(ID_TOWN_SELECTED)] ?: "",
                nameGasolinaSeleccionada = preference [stringPreferencesKey(NAME_GASOLINE_SELECTED)] ?: "",

            )
        }
    }


    companion object {
        const val GASOLINE_KEY = "gasoline_key"
        const val ID_PROVINCE_KEY = "id_province_key"
        const val PROVINCE_KEY = "province_key"
        const val TOWN_KEY = "town_key"
        const val ID_GASOLINE_SELECTED = "id_gasoline_selected"
        const val ID_TOWN_SELECTED = "id_town_selected"
        const val NAME_GASOLINE_SELECTED = "name_gasoline_selected"
    }
}


data class PreviewSearch(
    var gasoline: String,
    var province: String,
    var town: String,
    var idGasolinaSeleccionada: String,
    var idProvinciaSeleccionada: String,
    var idMunicipioSeleccionado: String,
    var nameGasolinaSeleccionada: String
)