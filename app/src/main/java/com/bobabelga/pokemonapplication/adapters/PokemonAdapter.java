package com.bobabelga.pokemonapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bobabelga.pokemonapplication.R;
import com.bobabelga.pokemonapplication.model.Pokemon;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private ArrayList<Pokemon> pokemonArrayList =new ArrayList<>();
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.pokemonNameTextView.setText(pokemonArrayList.get(position).getName());
        Glide.with(context).load(pokemonArrayList.get(position).getUrl())
                .into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
    }

    public void setPokemonArrayList(ArrayList<Pokemon> pokemonArrayList) {
        this.pokemonArrayList = pokemonArrayList;
        notifyDataSetChanged();
    }

    public Pokemon getPokemonAt(int position){
        return pokemonArrayList.get(position);
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImageView;
        TextView pokemonNameTextView;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView = itemView.findViewById(R.id.pokemon_imageView);
            pokemonNameTextView = itemView.findViewById(R.id.pokemon_name_text);

        }
    }
}
