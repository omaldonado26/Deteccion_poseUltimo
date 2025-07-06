package com.example.deteccion_pose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class RecomendacionesActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private Button btnIrADeteccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);

        toolbar = findViewById(R.id.toolbar_recomendaciones);
        setSupportActionBar(toolbar);

        btnIrADeteccion = findViewById(R.id.btn_ir_a_deteccion);
        btnIrADeteccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecomendacionesActivity.this, LivePreviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
