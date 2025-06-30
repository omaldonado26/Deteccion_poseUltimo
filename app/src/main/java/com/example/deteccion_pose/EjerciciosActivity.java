package com.example.deteccion_pose;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

public class EjerciciosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        Toolbar toolbar = findViewById(R.id.toolbar_ejercicios);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ejercicios recomendados");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtEjercicios = findViewById(R.id.txt_ejercicios);
        txtEjercicios.setText(
                "✅ Estiramiento de cuello\n\n" +
                        "➡ Gira lentamente la cabeza de un lado a otro. Mantén cada lado durante 10 segundos.\n\n" +
                        "✅ Estiramiento de hombros\n\n" +
                        "➡ Sube los hombros como si quisieras tocar tus orejas, mantén 5 segundos y baja lentamente.\n\n" +
                        "✅ Postura de la cobra\n\n" +
                        "➡ Acuéstate boca abajo y empuja tu pecho hacia arriba con las manos. Mantén por 15 segundos.\n\n" +
                        "✅ Repetir 3 veces cada uno a diario para mejores resultados."
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
