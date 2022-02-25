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
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {
    private PokemonViewModel pokemonViewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerView = findViewById(R.id.fav_recyclerView);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        setupSwipe();

        Button toHomeBtn = findViewById(R.id.to_home_button);
        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavActivity.this,MainActivity.class));
            }
        });

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.getFavPokemon();

        pokemonViewModel.getFavList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                pokemonAdapter.setPokemonArrayList((ArrayList<Pokemon>) pokemons);
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
                pokemonViewModel.deletePokemon(swipePokemon.getName());
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "Pokemon Deleted From Favorite",
                        Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}