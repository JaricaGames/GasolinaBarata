package com.jarica.preciogasolina.data.network.Retrofit

import com.jarica.preciogasolina.data.network.Retrofit.response.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MainService @Inject constructor(private val mainClient: MainClient) {

    suspend fun getProvincias(): List<Province> {
        return withContext(Dispatchers.IO) {
            val response = mainClient.getProvincias()
            response
        }
    }

    suspend fun getTownsByProvice(ID:String): List<Towns> {
        return withContext(Dispatchers.IO) {
            val response = mainClient.getTownsbyProvince(ID)
            response
        }
    }

    suspend fun getGasStationsByTowns(ID: String) : List<GasolineraPorMunicipio> {

        return withContext(Dispatchers.IO) {
            val response = mainClient.getGasStationsByTown(ID)
            response.body()!!.ListaEESSPrecio
        }
    }

    suspend fun getGasolines(): List<Gasolina> {

        return withContext(Dispatchers.IO) {
            val response = mainClient.getGasolines()
            response
        }
    }

    suspend fun getGasStationsByTownsAndGasoline(idTown: String, idGasoline: String): List<GasolineraPorGasolinaYMunicipio> {

        return withContext(Dispatchers.IO) {
            val response = mainClient.getGasStationsByTownAndGasoline(idTown,idGasoline)
            response.body()!!.ListaEESSPrecio
        }
    }


    suspend fun getESS(): MainResponse {
        return withContext(Dispatchers.IO) {
            val response = mainClient.getESS()
            response
        }
    }

}






