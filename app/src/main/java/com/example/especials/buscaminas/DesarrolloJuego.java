package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;


public class DesarrolloJuego extends Activity implements FragmentParrilla.CasillaListener{

    public int numberOfColumnsInMineField;

    public Partida partida;

    private Tablero tablero;


    public int secondsPassed;
    private boolean isTimerStarted = true;
    public boolean isFirtsClick = false;
    private Handler timer = new Handler();
    private TextView txtTimer;
    private boolean useTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo_juego);
        tablero = Tablero.getTablero();
        if(savedInstanceState == null)
            tablero.clearTablero();
        setPreferencies();
        if(savedInstanceState == null){
            isFirtsClick = true;
            isTimerStarted=false;
            secondsPassed = 0;
            startNewGame();
            log(getString(R.string.log_alias) + " " + partida.alias + " " + getString(R.string.log_casillas) + " " + partida.numeroCasillas + " " + getString(R.string.log_porcientominas) + " " + partida.porCientoMinas + "% " + getString(R.string.log_minas) + " " + partida.numBombas + "\n");
        }else{
            secondsPassed = savedInstanceState.getInt("secondsPassed");
            isFirtsClick = savedInstanceState.getBoolean("isFirtsClick");
            isTimerStarted = savedInstanceState.getBoolean("isTimerStarted");
            activarTablero();
            if(isTimerStarted) startTimer();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("secondsPassed",secondsPassed);
        outState.putBoolean("isFirtsClick", isFirtsClick);
        outState.putBoolean("isTimerStarted", isTimerStarted);
    }

    private void startNewGame()
    {
        createMineField();
        activarTablero();
        stopTimer();

    }

    private void setPreferencies() {

        partida = tablero.partida;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        partida.alias = pref.getString("alias","DefaultAlias");
        partida.porCientoMinas = Integer.valueOf(pref.getString("%minas", "25"));
        numberOfColumnsInMineField = Integer.valueOf(pref.getString("tamañoParrilla", "5"));
        useTimer = pref.getBoolean("controlTiempo", false);
        partida.numeroCasillas = numberOfColumnsInMineField * numberOfColumnsInMineField;
        txtTimer = (TextView) findViewById(R.id.textView6);
        partida.numBombas = (int)((partida.porCientoMinas / 100.0) * partida.numeroCasillas);
        tablero.textView = (TextView) findViewById(R.id.textoMinas);
    }


    private void activarTablero() {
        try {
            GridView parrilla;
            FragmentParrilla fgpar = (FragmentParrilla) getFragmentManager().findFragmentById(R.id.fragmentParrilla);
            parrilla = (GridView) fgpar.getView().findViewById(R.id.tablero);
            parrilla.setNumColumns(numberOfColumnsInMineField);
            CasillaAdapter adapter = new CasillaAdapter(this, tablero.casillas);
            parrilla.setAdapter(adapter);
            activateLog();
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
        for(int i = 0; i < partida.numeroCasillas; i++){
                Casilla c = new Casilla(img,numberOfColumnsInMineField,partida.numeroCasillas,this);
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
            String s = getString(R.string.log_casilla) + " " + toCoordenate(c.getPosition()) + " " + getString(R.string.log_abierta);
            if(useTimer) s += " " + getString(R.string.log_segundo) + " " + secondsPassed;
            log(s + "\n");
            c.openBlock();
            if (c.isMined()) {
                c.setBomb();
                gameOver(false, c.getPosition());
            }
            if(checkWin()) gameOver(true,c.getPosition());
        }else{
            System.out.println("No es clicable");
        }
    }

    private void onLongClick(Casilla c) {
        if(c.isCovered()) {
            if (!c.isFlagged() && !c.isQuestionMarked()) {//Poso "bandera"
                if (tablero.numBombes > 0) c.putFlag();
                else c.putQuestionMarked();
            } else if (c.isFlagged()) {//Poso "?"
                c.putQuestionMarked();
            } else {
                c.clean();
            }
        }
    }

    private void putBombs(Casilla casilla) {
        int numBombs = partida.numBombas;
        Casilla ca;
        tablero.numBombes = numBombs;
        tablero.casillas.get(0).updateBombs();
        System.out.println("numBombs "+numBombs);
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

    private void gameOver(boolean victoria, int position) {
        stopTimer();
        int n = casillasCovered();
        int b = bombasFlagged();
        fin(victoria, n ,b,position);
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

    public void fin(boolean victoria, int n, int bo, int position){
        Intent in = new Intent(this,Resultados.class);
        Bundle b = new Bundle();
        partida.numeroBanderasOK = bo;
        partida.tiempo = secondsPassed;
        if(victoria){
            partida.resultado = getString(R.string.log_victoria);
            partida.bomba = null;
            partida.numeroCasillasRestantes = 0;
            log(partida.resultado + "\n");
        } else {
            partida.resultado = getString(R.string.log_derrota);
            partida.bomba = toCoordenate(position);
            partida.numeroCasillasRestantes = partida.numeroCasillas - partida.numeroBanderasOK - n;
            log(partida.resultado + " " + getString(R.string.log_bombaEnCasilla) + " " + toCoordenate(position) + "\n");

        }

        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);

        b.putInt("banderasOK", bo);
        b.putBoolean("smartphone",fglog == null);


        in.putExtras(b);
        startActivity(in);
        finish();
    }

    public void startTimer()
    {
        if(secondsPassed==0 && !isFirtsClick && useTimer) {
            log(getString(R.string.log_tiempoON)+"\n");
            timer.removeCallbacks(updateTimeElasped);
            // tell timer to run call back after 1 second
        }
        if(!isFirtsClick && useTimer)
            timer.postDelayed(updateTimeElasped, 1000);
    }

    public void stopTimer(){
        // disable call backs
        if(secondsPassed != 0){
            log(getString(R.string.log_tiempoOFF) + " " + secondsPassed + "\n");
        }
        timer.removeCallbacks(updateTimeElasped);
    }

    // timer call back when timer is ticked
    private Runnable updateTimeElasped = new Runnable()
    {
        public void run(){
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
        partida.setToLog(text);
        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);
        if (fglog != null && fglog.isInLayout()) {
            fglog.log(partida.getLog());
        }
    }

    private void activateLog(){
        FragmentLog fglog = (FragmentLog) getFragmentManager().findFragmentById(R.id.fragmentLog);
        if (fglog != null && fglog.isInLayout()) {
            fglog.log(partida.getLog());
        }
    }

    private String toCoordenate(int position){
        String s = "(";
        s += getX(position)+", ";
        s += getY(position) + ")";
        return s;

    }

    private String getX(int position) {
        return String.valueOf(position%numberOfColumnsInMineField);
    }

    private String getY(int position) {
        return String.valueOf(position/numberOfColumnsInMineField);
    }


}
