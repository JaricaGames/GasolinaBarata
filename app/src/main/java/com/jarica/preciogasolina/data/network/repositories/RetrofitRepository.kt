package com.jarica.preciogasolina.data.network.repositories

import com.google.gson.reflect.TypeToken
import com.jarica.preciogasolina.data.local.DailyJsonCache
import com.jarica.preciogasolina.data.network.Retrofit.MainService
import com.jarica.preciogasolina.data.network.Retrofit.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject


class RetrofitRepository @Inject constructor(
    private val api: MainService,
    private val cache: DailyJsonCache
) {

    //DEVUELVE LAS PROVINCIAS
    suspend fun getProvincias(): List<Province> =
        cached("provincias", object : TypeToken<List<Province>>() {}.type) {
            api.getProvincias()
        }

    //DEVUELVE TODAS LAS EESS, SE USA PARA PINTAR LOS FAVORITOS
    suspend fun getEESS(): MainResponse =
        cached("eess", object : TypeToken<MainResponse>() {}.type) {
            api.getESS()
        }

    //DEVUELVE LOS MUNICIPIOS DE UNA PROVINCIA
    suspend fun getTownsbyProvince(ID: String): List<Towns> =
        cached("municipios_$ID", object : TypeToken<List<Towns>>() {}.type) {
            api.getTownsByProvice(ID)
        }

    //DEVUELVE LAS EESS DE UN MUNICIPIO
    suspend fun getGasStationsByTowns(ID: String): List<GasolineraPorMunicipio> {
        return api.getGasStationsByTowns(ID)
    }

    //DEVUELVE LOS TIPOS DE GASOLINA
    suspend fun getGasolines(): List<Gasolina> =
        cached("gasolinas", object : TypeToken<List<Gasolina>>() {}.type) {
            api.getGasolines()
        }

    //DEVUELVE LAS EESS POR MUNICIPIO Y TIPO DE GASOLINA
    suspend fun getGasStationsByTownsAndGasoline(IDTown: String, IDGasoline: String): List<GasolineraPorGasolinaYMunicipio> {
        return api.getGasStationsByTownsAndGasoline(IDTown, IDGasoline)
    }

    //DEVUELVE LA CACHE DEL DIA SI EXISTE; SI NO, LLAMA A LA API Y GUARDA EL JSON.
    //SI LA API FALLA (SIN CONEXION) SE USA LA CACHE DE DIAS ANTERIORES COMO RESPALDO.
    private suspend fun <T : Any> cached(key: String, type: Type, fetch: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            cache.read(key, type) ?: try {
                fetch().also { cache.write(key, it) }
            } catch (e: Exception) {
                cache.read(key, type, allowStale = true) ?: throw e
            }
        }

}
