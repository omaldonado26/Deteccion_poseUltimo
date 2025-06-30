package com.example.deteccion_pose;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnRecomendaciones = findViewById(R.id.btn_recomendaciones);
        Button btnEjercicios = findViewById(R.id.btn_ejercicios);
        Button btnContacto = findViewById(R.id.btn_contacto);

        btnRecomendaciones.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecomendacionesActivity.class);
            startActivity(intent);
        });

        btnEjercicios.setOnClickListener(v -> {
            Intent intent = new Intent(this, EjerciciosActivity.class);
            startActivity(intent);
        });

        btnContacto.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactoActivity.class);
            startActivity(intent);
        });
    }
}