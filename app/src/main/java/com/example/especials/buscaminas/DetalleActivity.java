package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DetalleActivity extends Activity {

    public static final String EXTRA_TEXTO =
            "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        RegistroFrag registroFrag = (RegistroFrag) getFragmentManager().findFragmentById(R.id.fragmentRegistro);
        registroFrag.mostrarRegistro(getIntent().getStringExtra(EXTRA_TEXTO));
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goAccesoBD(null);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void goAccesoBD(View v){
        Intent in = new Intent(DetalleActivity.this,AccesoBDActivity.class);
        startActivity(in);
        finish();
    }

}
