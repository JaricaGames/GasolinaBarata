package com.jarica.preciogasolina.domain

import com.jarica.preciogasolina.data.network.repositories.RoomRepository
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val roomRepository: RoomRepository) {
    operator fun invoke():Flow<List<FavoriteModel>> = roomRepository.favorites
}