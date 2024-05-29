package com.example.libreriaulisse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ricerca extends AppCompatActivity {

    ArrayList<ModelloLibro> modelloLibri = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView tornaHome;
    EditText ricercaProdotto;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String ricerca;
    ImageButton ricerca2;
    ImageView cart2;

    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        tornaHome = findViewById(R.id.tornaHome);
        ricercaProdotto = findViewById(R.id.ricercaProdotto);
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        String url = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/ricerca.php";
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //this.context = this;

        this.ricerca2 = this.findViewById(R.id.findBtn);
        this.ricerca2.setOnClickListener((View view) -> {
            ricercaProducts();
            //Toast.makeText(context,"CLICCC", Toast.LENGTH_SHORT).show();
        });

        tornaHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Ricerca.this, Home.class);
                startActivity(intent1);
            }
        });
        cart2 = findViewById(R.id.cart2);
        cart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ricerca.this, Carrello.class);
                startActivity(intent);
            }
        });
    }

    public void ricercaProducts(){
        //Log.i("ENTRATO","");
        ricerca = ricercaProdotto.getText().toString().trim();
        String ricercaUrl = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/ricerca.php?ricerca=" + ricerca;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ricercaUrl, null,
                response -> {
                    //Log.i("RESPONSE", response.toString());
                    try {
                        JSONArray libri = response.getJSONArray("Libri");
                        Log.i("LIBRI", libri.toString());
                        for (int i = 0; i < libri.length(); i++) {
                            JSONObject libro = libri.getJSONObject(i);
                            Integer id = libro.getInt("id");
                            String titolo = libro.getString("titolo");
                            String autore = libro.getString("autore");
                            String urlImage = libro.getString("url");
                            String genere = libro.getString("genere");
                            String descrizione = libro.getString("descrizione");
                            Double prezzo = libro.getDouble("prezzo");
                            Integer quantita = libro.getInt("quantita");

                            modelloLibri.add(new ModelloLibro(id, titolo, autore, genere, descrizione, prezzo, quantita, urlImage));
                        }
                        //Log.i("Dati", modelloLibro.toString());
                        modelloLibri.forEach(e-> Log.i("Libri", e.toString()));
                        LibroAdapter adapter = new LibroAdapter(modelloLibri, (int id, int qty) -> {
                            //Log.i("addClick", id+":" + qty);
                            addProductRequest(id,qty);
                        });
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e("Errore", e.getMessage());
                    }
                },
                error -> Log.e("Error", error.getMessage())
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void addProductRequest(int id, int qty){
        String addUrl = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/addrmv.php?id=" + id + "&add=" + qty;
        StringRequest request = new StringRequest(Request.Method.POST, addUrl,
                (response) -> {
                    Log.i("RESPONSE", response);
                },
                (error)-> {
                    Log.e("Error", error.getMessage());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("session_id", sharedPreferences.getString("session_id", null));
                return params;
            }
        };
        requestQueue.add(request);
    }

}