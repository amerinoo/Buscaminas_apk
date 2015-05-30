package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DesarrolloJuego extends Activity {
    private Bundle b;
    public static List<Casilla> casillas;
    private boolean isGameOver;
    public static int porcientominas;
    public static String alias;

    public static int numberOfColumnsInMineField;
    public static int totalNumberOfMines;
    public static int numCasillas;

    public GridView tablero;
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
        textView = (TextView) findViewById(R.id.textoMinas);
        tablero = (GridView) findViewById(R.id.tablero);
        tablero.setNumColumns(numberOfColumnsInMineField);
        CasillaAdapter adapter = new CasillaAdapter(this,casillas);
        tablero.setAdapter(adapter);
        context = this;

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

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void fin(View v){
        Intent in = new Intent(DesarrolloJuego.this,Resultados.class);
        Bundle b = new Bundle();

        b.putString("alias",alias);
        b.putInt("bombas", totalNumberOfMines);
        b.putInt("casillas",numCasillas);
        b.putInt("bombasTotales",numBombs);
        b.putInt("porciento",porcientominas);
        in.putExtras(b);
        startActivity(in);
        finish();
    }

}
