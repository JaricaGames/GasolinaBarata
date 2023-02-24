package com.jarica.preciogasolina.data.network

import com.jarica.preciogasolina.data.network.response.*
import javax.inject.Inject


class MainRepository @Inject constructor(private val api:MainService) {

    suspend fun getProvincias(): List<Province> {
        return api.getProvincias()
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