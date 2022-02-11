package com.example.pago_payphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class opc_inicio extends AppCompatActivity {
Button btnVerificar, btnRealizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opc_inicio);
        getSupportActionBar().hide();
        btnRealizar=(Button) findViewById(R.id.btnCompra);
        btnVerificar=(Button) findViewById(R.id.btnTransaccion);
        btnRealizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(opc_inicio.this, MainActivity.class);
                opc_inicio.this.startActivity(intent);
            }
        });
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(opc_inicio.this, solicitud.class);
                opc_inicio.this.startActivity(intent);
            }
        });
    }
}