package com.example.especials.buscaminas;

import android.app.Activity;
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

    public Partida partida;


    static TextView textView;
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
        b = getIntent().getExtras();
        porcientominas = b.getInt("minas");
        numberOfColumnsInMineField = b.getInt("tamañoParrilla");
        alias = b.getString("alias");
        useTimer = b.getBoolean("tiempo");
        numCasillas = numberOfColumnsInMineField * numberOfColumnsInMineField;
        txtTimer = (TextView) findViewById(R.id.textView6);
        totalNumberOfMines = (int)((porcientominas / 100.0) * numCasillas);
        if(savedInstanceState == null){
            isFirtsClick = true;
            isTimerStarted=false;
            tablero.clearTablero();
            secondsPassed = 0;
            startNewGame();
            log("Alias: " + alias + " Casillas: " + numCasillas + " %Minas: " + porcientominas + "% Minas: " + totalNumberOfMines + "\n");
        }else{
            secondsPassed = savedInstanceState.getInt("secondsPassed");
            activarTablero();
            startTimer();
        }
        textView = (TextView) findViewById(R.id.textoMinas);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("secondsPassed",secondsPassed);
    }

    private void startNewGame()
    {
        createMineField();
        activarTablero();
        stopTimer();
        partida = tablero.partida;
        partida.alias = alias;
        partida.numeroCasillas = numCasillas;
        partida.porCientoMinas = porcientominas;
        partida.numBombas = totalNumberOfMines;

    }


    private void activarTablero() {
        try {
            GridView parrilla;
            FragmentParrilla fgpar = (FragmentParrilla) getFragmentManager().findFragmentById(R.id.fragmentParrilla);
            parrilla = (GridView) fgpar.getView().findViewById(R.id.tablero);
            parrilla.setNumColumns(numberOfColumnsInMineField);
            CasillaAdapter adapter = new CasillaAdapter(this, tablero.casillas);
            parrilla.setAdapter(adapter);
            partida = tablero.partida;
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
            String s = "Casilla " + toCoordenate(c.getPosition()) + " abierta";
            if(useTimer) s += " en el segundo " + secondsPassed;
            log(s + "\n");
            c.openBlock();
            if (c.isMined())  gameOver(false,c.getPosition());
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
        int numBombs = totalNumberOfMines;
        Casilla ca;
        tablero.numBombes = numBombs;
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
            partida.resultado = "Victoria";
            partida.bomba = null;
            partida.numeroCasillasRestantes = partida.numeroCasillas - partida.numeroBanderasOK - n;
            log(partida.resultado + "\n");
        }else{
            partida.resultado = "Derrota";
            partida.bomba = toCoordenate(position);
            partida.numeroCasillasRestantes = 0;
            log(partida.resultado + " Bomba en casilla " + toCoordenate(position) + "\n");
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
        if(secondsPassed==0) {
            log("Timer engegat\n");
            timer.removeCallbacks(updateTimeElasped);
            // tell timer to run call back after 1 second
        }
        timer.postDelayed(updateTimeElasped, 1000);
    }

    public void stopTimer(){
        // disable call backs
        if(secondsPassed != 0){
            log("Tiempo usado " + secondsPassed + "\n");
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
