package com.jarica.preciogasolina.data.network.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey
    var id:String
)
