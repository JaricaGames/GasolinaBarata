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
