package com.bobabelga.pokemonapplication.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bobabelga.pokemonapplication.model.Pokemon;
import com.bobabelga.pokemonapplication.model.PokemonResponse;
import com.bobabelga.pokemonapplication.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PokemonViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favList = null;

    @Inject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    public void getPokemons(){
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for(Pokemon pokemon:list){
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"
                                    + pokemonIndex[pokemonIndex.length-1]
                                    + ".png");
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> pokemonList.setValue(result),
                        error-> Log.e("ViewModel",error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }
    public void deletePokemon(String pokemonName){
        repository.deletePokemon(pokemonName);
    }
    public void getFavPokemon(){
        favList = repository.getFavPokemon();
    }
}
