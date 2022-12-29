package com.jarica.preciogasolina.data.network

import android.util.Log
import com.jarica.preciogasolina.data.network.response.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MainService @Inject constructor(private val mainClient: MainClient) {


    suspend fun getEstaciones(): GasByTownResponse {
        return withContext(Dispatchers.IO) {
            val response = mainClient.getGasolineras()
            Log.i("Nono", response.body().toString())
            response.body()!!
        }
    }


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
            Log.i("Nono2", response.body().toString())
            Log.i("Nono3", response.body()!!.ListaEESSPrecio.toString())
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
            Log.i("nono", response.body()!!.ListaEESSPrecio.toString())
            response.body()!!.ListaEESSPrecio
        }
    }

}






