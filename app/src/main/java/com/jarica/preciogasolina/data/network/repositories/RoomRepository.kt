package com.jarica.preciogasolina.data.network.repositories

import com.jarica.preciogasolina.data.network.Room.FavoriteDAO
import com.jarica.preciogasolina.data.network.Room.FavoriteEntity
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(private val favoriteDAO: FavoriteDAO) {

    //Al devolver un FLOW no podemos hacer un metodo y nos enganchaamos por una variable que mapeamos
    val favorites:Flow<List<FavoriteModel>> = favoriteDAO.getFavorites().map { items-> items.map { FavoriteModel(it.id) }   }

    //Metodo que añade un favorito pasado por parametros a la BD
    suspend fun addFavorite(favoriteModel : FavoriteModel){
        favoriteDAO.addFavorite(FavoriteEntity(favoriteModel.id))
    }

    //Metodo que borra un favorito pasado por parametros a la BD
    suspend fun deleteFavorite(favoriteModel : FavoriteModel) {
        favoriteDAO.deleteFavorite(FavoriteEntity(favoriteModel.id))
    }

}