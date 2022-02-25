package com.bobabelga.pokemonapplication.network;

import com.bobabelga.pokemonapplication.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {

    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();
}
