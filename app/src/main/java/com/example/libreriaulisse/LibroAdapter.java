package com.example.libreriaulisse;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.ViewHolder> {

    private ArrayList<ModelloLibro> modelloLibri;
    private LibroAdapter.addToCartCallback aggiungi;

    public LibroAdapter(ArrayList<ModelloLibro> modelloLibri, final LibroAdapter.addToCartCallback aggiungi) {
        this.modelloLibri = modelloLibri;
        this.aggiungi = aggiungi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modellocarta, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelloLibro libro = modelloLibri.get(position);
        holder.title.setText(libro.getTitolo());
        holder.autore.setText(libro.getAutore());
        holder.img.loadUrl(libro.getAdapterUrl());
        holder.genere.setText(libro.getGenere());
        holder.descrizione.setText(libro.getDescrizione());
        String prezzo2Decimali = String.format(Locale.getDefault(), "%.2f", libro.getPrezzo());
        holder.price.setText(prezzo2Decimali + "€");

        holder.add.setOnClickListener((View view) -> {
            aggiungi.addToCart(libro.getId(), Integer.parseInt(holder.quantita.getText().toString()));
        });
    }

    @Override
    public int getItemCount() {
        return modelloLibri.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        WebView img;
        TextView title;
        TextView price;
        TextView autore;
        TextView genere;
        TextView descrizione;
        EditText quantita;
        Button add;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.titolo);
            this.autore = itemView.findViewById(R.id.autore);
            this.img = itemView.findViewById(R.id.imageView);
            this.genere = itemView.findViewById(R.id.genere);
            this.descrizione = itemView.findViewById(R.id.descrizione);
            this.price = itemView.findViewById(R.id.prezzo);
            this.quantita = itemView.findViewById(R.id.quantita);
            this.add = itemView.findViewById(R.id.aggiungi);
            this.cardView = (CardView) itemView;
        }
    }

    public interface addToCartCallback {
        void addToCart(int id, int qty);
    }
}