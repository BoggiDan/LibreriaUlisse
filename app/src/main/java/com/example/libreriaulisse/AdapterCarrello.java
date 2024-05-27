package com.example.libreriaulisse;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
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

public class AdapterCarrello extends RecyclerView.Adapter<AdapterCarrello.ViewHolder> {

    private ArrayList<ModelloLibro> modelloLibri;
    private AdapterCarrello.removeCallback rimuovi;

    public AdapterCarrello(ArrayList<ModelloLibro> modelloLibri, final AdapterCarrello.removeCallback rimuovi) {
        this.modelloLibri = modelloLibri;
        this.rimuovi = rimuovi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartacarrello, parent, false);
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
        holder.quantita.setText(libro.getQuantita() + "");

        holder.remove.setOnClickListener((View view) -> {
            rimuovi.removeToCart(libro.getId());
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
        TextView quantita;
        Button remove;
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
            this.remove = itemView.findViewById(R.id.rimuovi);
            this.cardView = (CardView) itemView;
        }
    }

    public interface removeCallback {
        void removeToCart(int id);
    }
}