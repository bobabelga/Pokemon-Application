package com.bobabelga.pokemonapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bobabelga.pokemonapplication.model.Pokemon;

@Database(entities = Pokemon.class, version = 1, exportSchema = false)
public abstract class PokemonDb extends RoomDatabase {
    public abstract PokemonDao pokemonDao();

}
