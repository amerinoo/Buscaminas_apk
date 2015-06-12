package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AccesoBDActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_bd);
    }

    public void goMainActivityBD(View v){
        Intent in = new Intent(AccesoBDActivity.this,MainActivity.class);
        startActivity(in);
        finish();
    }

}
