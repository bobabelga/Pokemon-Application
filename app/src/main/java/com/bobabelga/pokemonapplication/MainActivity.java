package com.bobabelga.pokemonapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bobabelga.pokemonapplication.adapters.PokemonAdapter;
import com.bobabelga.pokemonapplication.model.Pokemon;
import com.bobabelga.pokemonapplication.viewmodels.PokemonViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.pokemon_recyclerView);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        setupSwipe();

        Button toFavBtn = findViewById(R.id.to_fav_button);
        toFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FavActivity.class));
            }
        });

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.getPokemons();

        pokemonViewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                pokemonAdapter.setPokemonArrayList(pokemons);
            }
        });
    }
    private void setupSwipe(){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon swipePokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition);
                pokemonViewModel.insertPokemon(swipePokemon);
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Pokemon Added To Favorite",
                        Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}