package com.jarica.preciogasolina.data.network.repositories

import com.jarica.preciogasolina.data.network.Retrofit.MainService
import com.jarica.preciogasolina.data.network.Retrofit.response.*
import javax.inject.Inject


class RetrofitRepository @Inject constructor(private val api: MainService) {

    suspend fun getProvincias(): List<Province> {
        return api.getProvincias()
    }

    suspend fun getEESS(): MainResponse {
        return api.getESS()
    }

    suspend fun getTownsbyProvince(ID:String): List<Towns>{
        return api.getTownsByProvice(ID)
    }

    suspend fun getGasStationsByTowns(ID:String) : List<GasolineraPorMunicipio> {
        return api.getGasStationsByTowns(ID)
    }

    suspend fun getGasolines() : List<Gasolina>{
        return api.getGasolines()
    }

    suspend fun getGasStationsByTownsAndGasoline(IDTown: String, IDGasoline: String): List<GasolineraPorGasolinaYMunicipio> {
        return api.getGasStationsByTownsAndGasoline(IDTown, IDGasoline)
    }


}