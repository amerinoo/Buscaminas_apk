package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;


public class DesarrolloJuego extends Activity implements FragmentParrilla.CasillaListener{
    private Bundle b;
    public int porcientominas;
    public String alias;

    public int numberOfColumnsInMineField;
    public int totalNumberOfMines;
    public int numCasillas;

    public Context context;



    static TextView textView;
    public int numBombs;
    private Tablero tablero;


    public int secondsPassed = 0;
    private boolean isTimerStarted = false;
    public boolean isFirtsClick = true;
    private Handler timer = new Handler();
    private TextView txtTimer;
    private boolean useTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo_juego);
        if(savedInstanceState == null){
            b = getIntent().getExtras();
            porcientominas = b.getInt("minas");
            numberOfColumnsInMineField = b.getInt("tamañoParrilla");
            alias = b.getString("alias");
            useTimer = b.getBoolean("tiempo");
            numCasillas = numberOfColumnsInMineField * numberOfColumnsInMineField;
            isFirtsClick = true;
            secondsPassed = 0;
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
        txtTimer = (TextView) findViewById(R.id.textView6);
        createMineField();
        totalNumberOfMines = (int)((porcientominas / 100.0) * numCasillas);
        activarTablero();
        stopTimer();
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
                Casilla c = new Casilla(img,numberOfColumnsInMineField,numCasillas,this);
                c.setPosition(i);
                tablero.casillas.add(c);
        }

    }

    @Override
    public void onCasillaSeleccionada(Casilla c, boolean longClick) {
        if(isFirtsClick){
            putBombs(c);
            isFirtsClick = false;
        }
        if (useTimer && !isTimerStarted)
        {
            startTimer();
            isTimerStarted = true;
        }
        if (longClick) onLongClick(c);
        else onClick(c);
    }

    private void onClick(Casilla c) {

        if(c.isClickable()){
            log(String.valueOf(c.getPosition()));
            c.openBlock();
            if (c.isMined())  gameOver("No, has perdido");
            if(checkWin()) gameOver("Si, has ganado");
        }else{
            System.out.println("No es clicable");
        }
    }

    private void onLongClick(Casilla c) {
        if(c.isCovered()) {
            if (!c.isFlagged() && !c.isQuestionMarked()) {//Poso "bandera"
                if (c.getNumBombes() > 0) c.putFlag();
                else c.putQuestionMarked();
            } else if (c.isFlagged()) {//Poso "?"
                c.putQuestionMarked();
            } else {
                c.clean();
            }
        }
    }

    private void putBombs(Casilla casilla) {
        int numBombs = totalNumberOfMines;
        Casilla ca;
        tablero.casillas.get(0).setNumBombes(numBombs);
        tablero.casillas.get(0).updateBombs();
        System.out.println("numBombs"+numBombs);
        Random randomGenerator = new Random();
        int i =0;
        while (i<numBombs){
            int randomInt = randomGenerator.nextInt(tablero.casillas.size());
            ca = tablero.casillas.get(randomInt);
            if(!ca.isMined() && !ca.equals(casilla) ) {
                tablero.casillas.get(randomInt).setMined();
                i+=1;
            }
        }
        for (Casilla c: tablero.casillas){
            c.calculateCellsSurrounding();
        }
    }
    private boolean checkWin() {
        for (Casilla c: tablero.casillas){
            if(c.isCovered() && !c.isMined())  return false;
        }
        return true;
    }

    private void gameOver(String s) {
        stopTimer();
        int n = casillasCovered();
        int b = bombasFlagged();
        fin(s,n,b);


    }

    private int bombasFlagged() {
        int i = 0;
        for (Casilla c: tablero.casillas){
            if(c.isMined() && c.isFlagged())  i+=1;
        }
        return i;
    }

    private int casillasCovered() {
        int i = 0;
        for (Casilla c: tablero.casillas){
            if(!c.isCovered() && !c.isMined())  i+=1;
        }
        return i;
    }


    public void fin(String s, int n, int bo){
        Activity a = (Activity) context;
        Intent in = new Intent(context,Resultados.class);
        Bundle b = new Bundle();

        FragmentLog fglog = (FragmentLog) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentLog);
        TextView tv;
        if (fglog != null && fglog.isInLayout()) {
            tv = ((TextView) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentLog).getView().findViewById(R.id.TxtLog));
            b.putString("log", tv.getText().toString());
        }else {
            b.putString("alias", alias);
            b.putInt("bombas", totalNumberOfMines);
            b.putInt("casillas", numCasillas);
            b.putInt("bombasTotales", numBombs);
            b.putInt("porciento", porcientominas);
            b.putInt("tiempo", secondsPassed);
            b.putString("resultado", s);
            b.putInt("casillasRestantes", n);
            b.putInt("bombasRestantes", bo);
        }

        in.putExtras(b);
        a.startActivity(in);
        a.finish();
    }

    public void startTimer()
    {
        if(secondsPassed==0) {
            timer.removeCallbacks(updateTimeElasped);
            // tell timer to run call back after 1 second
            timer.postDelayed(updateTimeElasped, 1000);
        }
    }

    public void stopTimer(){
        // disable call backs
        timer.removeCallbacks(updateTimeElasped);
    }


    // timer call back when timer is ticked
    private Runnable updateTimeElasped = new Runnable()
    {
        public void run()
        {
            long currentMilliseconds = System.currentTimeMillis();
            ++secondsPassed;

            if (secondsPassed < 10) txtTimer.setText("00" + Integer.toString(secondsPassed));
            else if (secondsPassed < 100) txtTimer.setText("0" + Integer.toString(secondsPassed));
            else txtTimer.setText(Integer.toString(secondsPassed));

            // add notification
            timer.postAtTime(this, currentMilliseconds);
            // notify to call back after 1 seconds
            // basically to remain in the timer loop
            timer.postDelayed(updateTimeElasped, 1000);
        }
    };
    private void log(String text){
        FragmentLog fglog = (FragmentLog) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentLog);
        TextView tv;
        if (fglog != null && fglog.isInLayout()) {
            tv = ((TextView) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentLog).getView().findViewById(R.id.TxtLog));
            tv.setText(tv.getText().toString() + "\n" + text);
        }

    }
}
