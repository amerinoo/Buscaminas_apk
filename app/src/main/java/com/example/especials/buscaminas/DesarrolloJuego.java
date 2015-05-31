package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;


public class DesarrolloJuego extends Activity implements FragmentParrilla.CasillaListener{
    private Bundle b;
    public static int porcientominas;
    public static String alias;

    public static int numberOfColumnsInMineField;
    public static int totalNumberOfMines;
    public static int numCasillas;

    public static Context context;



    static TextView textView;
    public static boolean temporitzador;
    public static int numBombs;
    private Tablero tablero;

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

        if(savedInstanceState == null)firstLog();
        textView = (TextView) findViewById(R.id.textoMinas);
        context = this;

    }

    private void firstLog(){
        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);
        if(fglog != null && fglog.isInLayout())
            fglog.log("Alias: " + alias + " Casillas: " + numCasillas + " %Minas: " + porcientominas + "% Minas: " + totalNumberOfMines + "\n");
    }


    private void startNewGame()
    {
        tablero = Tablero.getTablero();
        createMineField();
        totalNumberOfMines = (int)((porcientominas / 100.0) * numCasillas);
        activarTablero();
    }

    private void activarTablero() {
        try {
            GridView parrilla;
            FragmentParrilla fgpar = (FragmentParrilla) getFragmentManager().findFragmentById(R.id.fragmentParrilla);

            parrilla = (GridView) fgpar.getView().findViewById(R.id.tablero);
            parrilla.setNumColumns(numberOfColumnsInMineField);
            CasillaAdapter adapter = new CasillaAdapter(this, tablero.casillas);
            parrilla.setAdapter(adapter);
        } catch (NullPointerException n){
            n.printStackTrace();
            throw new NullPointerException();
        }

    }


    private void createMineField() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(3);
        int img;
        if (randomInt == 0) img = R.drawable.bobby_bomb;
        else if (randomInt == 1) img = R.drawable.crazy_bomb;
        else  img = R.drawable.super_troll_bomb;
        for(int i = 0; i < numCasillas; i++){
                Casilla c = new Casilla(img);
                c.setPosition(i);
                tablero.casillas.add(c);
        }

    }

    @Override
    public void onCasillaSeleccionada(Casilla c, boolean longClick) {

    }
}
