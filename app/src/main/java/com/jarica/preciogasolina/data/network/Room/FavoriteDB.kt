package com.jarica.preciogasolina.data.network.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)

abstract class FavoriteDB:RoomDatabase() {
    abstract fun favoriteDao():FavoriteDAO
}