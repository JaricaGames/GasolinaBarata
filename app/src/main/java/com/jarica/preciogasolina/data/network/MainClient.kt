package com.jarica.preciogasolina.data.network


import com.jarica.preciogasolina.data.network.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainClient {

    @GET("EstacionesTerrestres/")
    suspend fun getGasolineras():Response<GasByTownResponse>

    @GET("Listados/Provincias/")
     suspend fun getProvincias(): List<Province>

     @GET("Listados/MunicipiosPorProvincia/{ID}")
     suspend fun getTownsbyProvince(@Path("ID") ID:String): List<Towns>

    @GET("EstacionesTerrestres/FiltroMunicipio/{IDMUNICIPIO}")
    suspend fun getGasStationsByTown(@Path("IDMUNICIPIO") ID:String): Response<GasByTownResponse>

    @GET("Listados/ProductosPetroliferos/")
    suspend fun getGasolines(): List<Gasolina>

    @GET("EstacionesTerrestres/FiltroMunicipioProducto/{IDMUNICIPIO}/{IDPRODUCTO}")
    suspend fun getGasStationsByTownAndGasoline(@Path("IDMUNICIPIO") idTown: String, @Path("IDPRODUCTO") idGasoline: String  ) : Response<GasByGasolineAndTownResponse>


}