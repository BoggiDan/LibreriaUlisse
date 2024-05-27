package com.example.libreriaulisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DatiAcquisto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati_acquisto);
        ImageView indietro = findViewById(R.id.back2);
        ImageView buyorder = findViewById(R.id.buyorder);









        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DatiAcquisto.this, Carrello.class);
                startActivity(intent1);
            }
        });

        buyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DatiAcquisto.this, Acquistato.class);
                startActivity(intent2);
            }
        });
    }
}