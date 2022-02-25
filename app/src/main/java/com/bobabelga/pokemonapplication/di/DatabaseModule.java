package com.bobabelga.pokemonapplication.di;

import android.app.Application;

import androidx.room.Room;

import com.bobabelga.pokemonapplication.db.PokemonDao;
import com.bobabelga.pokemonapplication.db.PokemonDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static PokemonDb provideDb (Application application){
        return Room.databaseBuilder(application,PokemonDb.class,"fav_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDb pokemonDb){
        return pokemonDb.pokemonDao();
    }
}
