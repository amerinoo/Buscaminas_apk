package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goAyuda(View v){
        Intent in = new Intent(MainActivity.this,Ayuda.class);
        startActivity(in);
        finish();
    }
    public void goEmpezarPartida(View v){
        Intent in = new Intent(MainActivity.this,Configuracion.class);
        startActivity(in);
        finish();
    }

    public void goConsultarBD(View v){
        Intent in = new Intent(MainActivity.this,AccesoBDActivity.class);
        startActivity(in);
        finish();
    }
    public void goSalir(View v){
        finish();
    }
}
