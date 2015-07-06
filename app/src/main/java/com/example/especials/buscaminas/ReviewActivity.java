package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


public class ReviewActivity extends Activity implements FragmentParrilla.CasillaListener{

    private Tablero tablero;
    private int numberOfColumnsInMineField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo_juego);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        tablero = Tablero.getTablero();
        tablero.textView = (TextView) findViewById(R.id.textoMinas);
        numberOfColumnsInMineField = (int)Math.sqrt(tablero.casillas.size());
        activarTablero();
        putLog();
        putTime();

    }

    private void putLog() {
        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);
        if(fglog != null && fglog.isInLayout()) fglog.log(tablero.partida.allLog);
    }

    private void putTime() {
        int tiempo = tablero.partida.tiempo;
        String stiempo;
        TextView txtTimer = (TextView) findViewById(R.id.textView6);
        if (tiempo < 10) stiempo = "00"+tiempo;
        else if (tiempo < 100) stiempo = "0"+tiempo;
        else stiempo = ""+tiempo;

        txtTimer.setText(stiempo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                goAccesoBDActivity(null);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void goAccesoBDActivity(View v){
        Intent in = new Intent(ReviewActivity.this,AccesoBDActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onCasillaSeleccionada(Casilla c, boolean longClick) {}

    private void activarTablero() {
        try {
            GridView parrilla;
            FragmentParrilla fgpar = (FragmentParrilla) getFragmentManager().findFragmentById(R.id.fragmentParrilla);
            parrilla = (GridView) fgpar.getView().findViewById(R.id.tablero);
            parrilla.setNumColumns(numberOfColumnsInMineField);
            ReviewAdapter adapter = new ReviewAdapter(this, tablero.casillas);
            parrilla.setAdapter(adapter);
        } catch (NullPointerException n){
            n.printStackTrace();
            throw new NullPointerException();
        }

    }
}
