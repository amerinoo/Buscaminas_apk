package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DesarrolloJuego extends Activity implements FragmentParrilla.CasillaListener{
    private Bundle b;
    public static List<Casilla> casillas;
    public static int porcientominas;
    public static String alias;

    public static int numberOfColumnsInMineField;
    public static int totalNumberOfMines;
    public static int numCasillas;

    public static Context context;



    static TextView textView;
    public static boolean temporitzador;
    public static int numBombs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo_juego);
        if(savedInstanceState == null){
            b = getIntent().getExtras();
            porcientominas = b.getInt("minas");
            numberOfColumnsInMineField = b.getInt("tamañoParrilla");
            alias = b.getString("alias");
            temporitzador = b.getBoolean("tiempo");
            numCasillas = numberOfColumnsInMineField * numberOfColumnsInMineField;
            CasillaAdapter.isFirtsClick = true;
            CasillaAdapter.secondsPassed = 0;
            startNewGame();
        }
        activarTablero();
        if(savedInstanceState == null)log("Alias: " + alias + " Casillas: " + numCasillas + " %Minas: " + porcientominas + "% Minas: " + totalNumberOfMines);
        textView = (TextView) findViewById(R.id.textoMinas);
        context = this;

    }

    private void activarTablero(){
        GridView tablero;
        FragmentParrilla fgpar = (FragmentParrilla) getFragmentManager().findFragmentById(R.id.fragmentParrilla);

        tablero = (GridView) fgpar.getView().findViewById(R.id.tablero);
        tablero.setNumColumns(numberOfColumnsInMineField);
        CasillaAdapter adapter = new CasillaAdapter(this,casillas);
        tablero.setAdapter(adapter);


    }

    private void startNewGame()
    {
        // plant mines and do rest of the calculations
        createMineField();
        totalNumberOfMines = (int)((porcientominas / 100.0) * numCasillas);
    }



    private void createMineField() {
        casillas = new ArrayList<>();
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(3);
        int img;
        if (randomInt == 0) img = R.drawable.bobby_bomb;
        else if (randomInt == 1) img = R.drawable.crazy_bomb;
        else  img = R.drawable.super_troll_bomb;
        for(int i = 0; i < numCasillas; i++){
                Casilla c = new Casilla(img);
                c.setPosition(i);
                casillas.add(c);
        }

    }

    @Override
    public void onCorreoSeleccionado(Casilla c) {

    }
    private void log(String text){
        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);
        TextView tv;
        if (fglog != null && fglog.isInLayout()) {
            tv = ((TextView) getFragmentManager().findFragmentById(R.id.fragmentLog).getView().findViewById(R.id.TxtLog));
            tv.setText(tv.getText().toString() + "\n" + text);
        }

    }
}
