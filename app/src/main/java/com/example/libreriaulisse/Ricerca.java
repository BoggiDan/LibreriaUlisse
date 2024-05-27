package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ricerca extends AppCompatActivity {

    ImageView tornaHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        tornaHome = findViewById(R.id.tornaHome);

        String url = "https://danieleboggian3g.altervista.org/sitoCarrelloQuery/api/ricerca.php";

        tornaHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Ricerca.this, Home.class);
                startActivity(intent1);
            }
        });
    }
}