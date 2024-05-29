package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    ArrayList<ModelloLibro> modelloLibri = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    String url = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/letturadb.php";
    TextView textView3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        textView3 = findViewById(R.id.textView3);
        textView3.bringToFront();
        ImageView ricerca = findViewById(R.id.ricerca);
        ImageView carrello = findViewById(R.id.carrello);
        ImageView logout = findViewById(R.id.logout);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

//try {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray libri = response.getJSONArray("Libri");
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
                        modelloLibri.forEach(e->Log.i("Dati", e.toString()));
                        LibroAdapter adapter = new LibroAdapter(modelloLibri, (int id, int qty) -> {
                           //Log.i("addClick", id+":" + qty);
                            addProductRequest(id,qty);
                        });
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                },
                error -> Log.e("Error", error.getMessage())
        );
        requestQueue.add(jsonObjectRequest);
        

    //}catch (Exception e){
    //Log.e("Error", e.getMessage());
//}


        ricerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Ricerca.class);
                startActivity(intent);
            }
        });

        carrello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Carrello.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
        });

    }

    // login
    // connessione al database
    // home, con tutti i libri, con nav dove c'è il nome della libreria "Libreria Ulisse"
    // quando clicchi sul tasto ricerca o carrello con degli intent ti manda all'altra activity
    // funzionalità di ricerca di un libro
    // libro si può aggiungere al carrello
    // carrello dove comprare i libri

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

    private void check(){
        StringRequest request = new StringRequest(Request.Method.GET, "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/check.php?add&print",
                response -> {
                    Log.i("Check", response.toString());
                },
                error -> Log.e("CheckErr", error.getMessage())
        );
        requestQueue.add(request);
    }
}