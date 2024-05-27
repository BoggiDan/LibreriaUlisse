package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

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

public class Carrello extends AppCompatActivity {

    ArrayList<ModelloLibro> modelloLibri = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    String url = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/stampacarrello.php";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        ImageView indietro = findViewById(R.id.back);
        ImageView verificato = findViewById(R.id.next);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
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
                        modelloLibri.forEach(e-> Log.i("Dati", e.toString()));
                        AdapterCarrello adapter = new AdapterCarrello(modelloLibri, (int id) -> {
                            //Log.i("rmvClick", id + "");
                            removeProductRequest(id,1);
                        });
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e("Errorr", e.getMessage());
                    }
                },
                error -> Log.e("Error", error.getMessage())
        );
        requestQueue.add(jsonObjectRequest);*/


        /*StringRequest request = new StringRequest(Request.Method.POST, url,
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
                        modelloLibri.forEach(e-> Log.i("Dati", e.toString()));
                        AdapterCarrello adapter = new AdapterCarrello(modelloLibri, (int id) -> {
                            //Log.i("rmvClick", id + "");
                            removeProductRequest(id,1);
                        });
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e("Errorr", e.getMessage());
                    }
                },
                error -> Log.e("Error", error.getMessage())
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("session_id", sharedPreferences.getString("session_id", null));
                return params;
            }
        };
        );
        requestQueue.add(jsonObjectRequest);*/

        stampaProdotti();

        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Carrello.this, Home.class);
                startActivity(intent1);
            }
        });

        verificato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Carrello.this, DatiAcquisto.class);
                startActivity(intent2);
            }
        });


    }

    private void removeProductRequest(int id, int qty){
        String removeUrl = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/addrmv.php?id=" + id + "&remove=" + qty;
        StringRequest request = new StringRequest(Request.Method.POST, removeUrl,
                (response) -> {
                    Log.i("Remove", response.toString());
                    stampaProdotti();
                },
                (error)-> {
                    Log.e("RemoveErr", error.getMessage());
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

    public void stampaProdotti(){
        modelloLibri.clear();
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray libri = jsonObject.getJSONArray("Libri");
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

                    } catch (Exception e) {
                        Log.e("Errorr", e.getMessage());
                    }
                    modelloLibri.forEach(e-> Log.i("Dati", e.toString()));
                    AdapterCarrello adapter = new AdapterCarrello(modelloLibri, (int id) -> {
                        //Log.i("rmvClick", id + "");
                        removeProductRequest(id,1);
                    });
                    recyclerView.setAdapter(adapter);
                },
                error -> Log.e("Error", error.getMessage())
        ){
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