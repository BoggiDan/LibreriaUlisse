package com.example.libreriaulisse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {
    private ArrayList<ModelloLibro> libroList;

    public static class LibroViewHolder extends RecyclerView.ViewHolder {
        public TextView titoloView;
        public TextView autoreView;
        public ImageView imageView;

        public LibroViewHolder(View itemView) {
            super(itemView);
            titoloView = itemView.findViewById(R.id.titolo);
            autoreView = itemView.findViewById(R.id.autore);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public LibroAdapter(ArrayList<ModelloLibro> libroList) {
        this.libroList = libroList;
    }

    @Override
    public LibroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.libro_item, parent, false);
        LibroViewHolder lvh = new LibroViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(LibroViewHolder holder, int position) {
        ModelloLibro currentItem = libroList.get(position);

        holder.titoloView.setText(currentItem.getTitolo());
        holder.autoreView.setText(currentItem.getAutore());
        // Load image using a library like Picasso or Glide
        // Example with Picasso:
        // Picasso.get().load(currentItem.getUrlImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return libroList.size();
    }
}
