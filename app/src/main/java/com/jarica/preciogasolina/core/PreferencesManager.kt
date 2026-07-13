package com.jarica.preciogasolina.core

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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


    //GUARDA UNA BUSQUEDA COMO LA MAS RECIENTE, SIN DUPLICADOS Y CON UN MAXIMO DE DOS
    suspend fun saveRecentSearch(search: RecentSearch) {
        context.dataStore.edit { preference ->
            val current = parseRecentSearches(preference[stringPreferencesKey(RECENT_SEARCHES_KEY)])
            val updated = (listOf(search) + current.filterNot { it.isSameSearch(search) }).take(2)
            preference[stringPreferencesKey(RECENT_SEARCHES_KEY)] = Gson().toJson(updated)
        }
    }

    //DEVUELVE LAS ULTIMAS BUSQUEDAS REALIZADAS (MAXIMO DOS)
    fun loadRecentSearches(): Flow<List<RecentSearch>> {
        return context.dataStore.data.map { preference ->
            parseRecentSearches(preference[stringPreferencesKey(RECENT_SEARCHES_KEY)])
        }
    }

    private fun parseRecentSearches(json: String?): List<RecentSearch> {
        if (json.isNullOrEmpty()) return emptyList()
        return try {
            Gson().fromJson(json, object : TypeToken<List<RecentSearch>>() {}.type)
        } catch (e: Exception) {
            emptyList()
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
        const val RECENT_SEARCHES_KEY = "recent_searches_key"
    }
}

@Keep
data class RecentSearch(
    val gasolineId: String,
    val gasolineName: String,
    val provinceId: String,
    val provinceName: String,
    val townId: String,
    val townName: String
) {
    //DOS BUSQUEDAS SON LA MISMA SI COINCIDEN MUNICIPIO Y CARBURANTE
    fun isSameSearch(other: RecentSearch) =
        townId == other.townId && gasolineId == other.gasolineId
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