package com.jarica.preciogasolina.core.di

import android.content.Context
import androidx.room.Room
import com.jarica.preciogasolina.data.network.Retrofit.MainClient
import com.jarica.preciogasolina.data.network.Room.FavoriteDAO
import com.jarica.preciogasolina.data.network.Room.FavoriteDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    //INYECCION DE RETROFIT
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //INYECCION DE INTERFAZ MAINCLIENT
    @Singleton
    @Provides
    fun provideMainClient(retrofit: Retrofit): MainClient {
        return retrofit.create(MainClient::class.java)
    }

    //INYECCION DE LA BASE DE DATOS DE ROOM
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext:Context): FavoriteDB{
        return Room.databaseBuilder(appContext, FavoriteDB::class.java, "FavoriteDB").build()
    }

    //INYECCION DEL DAO DE ROOM
    @Provides
    fun provideFavoriteDAO(favoriteDB: FavoriteDB): FavoriteDAO{
        return favoriteDB.favoriteDao()
    }

}