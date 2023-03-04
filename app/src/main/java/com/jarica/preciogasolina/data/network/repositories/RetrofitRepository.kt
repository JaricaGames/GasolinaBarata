package com.jarica.preciogasolina.data.network.repositories

import com.jarica.preciogasolina.data.network.Retrofit.MainService
import com.jarica.preciogasolina.data.network.Retrofit.response.*
import javax.inject.Inject


class RetrofitRepository @Inject constructor(private val api: MainService) {

    //DEVUELVE LAS PROVINCIAS
    suspend fun getProvincias(): List<Province> {
        return api.getProvincias()
    }

    //DEVUELVE TODAS LAS EESS, SE USA PARA PINTAR LOS FAVORITOS
    suspend fun getEESS(): MainResponse {
        return api.getESS()
    }

    //DEVUELVE LOS MUNICIPIOS DE UNA PROVINCIA
    suspend fun getTownsbyProvince(ID:String): List<Towns>{
        return api.getTownsByProvice(ID)
    }

    //DEVUELVE LAS EESS DE UN MUNICIPIO
    suspend fun getGasStationsByTowns(ID:String) : List<GasolineraPorMunicipio> {
        return api.getGasStationsByTowns(ID)
    }

    //DEVUELVE LOS TIPOS DE GASOLINA
    suspend fun getGasolines() : List<Gasolina>{
        return api.getGasolines()
    }

    //DEVUELVE LAS EESS POR MUNICIPIO Y TIPO DE GASOLINA
    suspend fun getGasStationsByTownsAndGasoline(IDTown: String, IDGasoline: String): List<GasolineraPorGasolinaYMunicipio> {
        return api.getGasStationsByTownsAndGasoline(IDTown, IDGasoline)
    }


}