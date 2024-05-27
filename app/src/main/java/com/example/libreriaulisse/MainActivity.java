package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/letturaid.php", null,
                response -> {
                    try {
                        editor.putString("session_id", response.getString("session_id"));
                        editor.apply();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Log.e("RemoveErr", error.getMessage())
        );
        requestQueue.add(request);

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}
