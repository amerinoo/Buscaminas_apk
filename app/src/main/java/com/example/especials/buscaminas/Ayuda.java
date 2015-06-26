package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Ayuda extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goMainActivity(null);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    public void goMainActivity(View v){
        Intent in = new Intent(Ayuda.this,MainActivity.class);
        startActivity(in);
        finish();
    }
}
