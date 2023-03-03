package com.jarica.preciogasolina.data.network.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {

    @Query("SELECT * from FavoriteEntity")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}