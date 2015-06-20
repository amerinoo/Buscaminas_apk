package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (featureId){
            case 0:
                goPreferencias();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void goPreferencias() {
        Intent in = new Intent(MainActivity.this,Preferencias.class);
        startActivity(in);
        finish();
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
