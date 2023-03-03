package com.jarica.preciogasolina.domain

import com.jarica.preciogasolina.data.network.repositories.RoomRepository
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val roomRepository: RoomRepository) {

    suspend operator fun invoke(favoriteModel: FavoriteModel) {
        roomRepository.deleteFavorite(favoriteModel)
    }
}