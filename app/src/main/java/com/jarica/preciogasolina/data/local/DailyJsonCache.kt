package com.jarica.preciogasolina.data.local

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

//CACHE EN DISCO DE LAS RESPUESTAS DE LA API, VALIDA DURANTE EL MISMO DIA NATURAL
@Singleton
class DailyJsonCache @Inject constructor(@ApplicationContext private val context: Context) {

    private val gson = Gson()

    //DEVUELVE EL JSON GUARDADO SI EXISTE (Y ES DE HOY, SALVO allowStale) O NULL
    fun <T> read(key: String, type: Type, allowStale: Boolean = false): T? {
        val file = fileFor(key)
        if (!file.exists()) return null
        if (!allowStale && !isToday(file.lastModified())) return null
        return try {
            gson.fromJson(file.readText(), type)
        } catch (e: Exception) {
            null
        }
    }

    fun write(key: String, data: Any) {
        try {
            val file = fileFor(key)
            file.parentFile?.mkdirs()
            file.writeText(gson.toJson(data))
        } catch (e: Exception) {
            //SI NO SE PUEDE ESCRIBIR LA CACHE SE SIGUE FUNCIONANDO CONTRA LA API
        }
    }

    private fun fileFor(key: String) = File(File(context.filesDir, "api_cache"), "$key.json")

    private fun isToday(epochMillis: Long): Boolean =
        Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate() == LocalDate.now()
}
