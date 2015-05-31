package com.example.especials.buscaminas;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Especials on 27/03/2015.
 */
public class CasillaAdapter extends BaseAdapter {

    private final List<Casilla> casillas;
    private final Context context;
    private Button firstButton;

    private Handler timer = new Handler();
    private static TextView txtTimer;
    public static int secondsPassed = 0;
    private boolean isTimerStarted = false;
    public static boolean isFirtsClick = true;
    private boolean useTimer;

    FragmentParrilla fgpar;

    public CasillaAdapter(Context context, List<Casilla> casillas) {
        this.casillas = casillas;
        this.context=context;
        txtTimer = (TextView)((Activity) context).getWindow().findViewById(R.id.textView6);
        fgpar = (FragmentParrilla) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentParrilla);
        createFirtsButton();
        useTimer = DesarrolloJuego.temporitzador;

        stopTimer();
    }

    private void createFirtsButton(){
        Casilla c = getItem(0);
        firstButton = new Button(context);
        firstButton.setPadding(8, 8, 8, 8);
        c.setButton(firstButton);
        c.setPosition(0);
        c.setBlockAsDisabled(false);
        firstButton.setOnClickListener(new OnClickListenerCasilla(c));
        firstButton.setOnLongClickListener(new OnLongClickListener(c));
        reload(c);
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return casillas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Casilla getItem(int position) {
        return casillas.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param view The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Button btn;
        Casilla c = getItem(position);
        if (view == null && position != 0) {
            btn = new Button(context);
            btn.setPadding(8, 8, 8, 8);

            c.setButton(btn);
            c.setPosition(position);
            c.setBlockAsDisabled(false);
            btn.setOnClickListener(new OnClickListenerCasilla(c));
            btn.setOnLongClickListener(new OnLongClickListener(c));
            reload(c);
        }else if(position == 0){ btn = firstButton;}
        else {
            btn = (Button) view;
            System.out.println(position);
        }
        return btn;
    }

    private void reload(Casilla c){
        if(c.isFlagged()) c.putFlag();
        else if (c.isQuestionMarked()) c.putQuestionMarked();
        else if (!c.isCovered()) c.putMinesInSurrounding();
    }

    private class OnClickListenerCasilla implements View.OnClickListener{
        private  Casilla c;

        private OnClickListenerCasilla(Casilla c) {
            this.c = c;
        }

        /**114
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            fgpar.onItemClick(c,false);
            if(isFirtsClick){
                putBombs(c);
                isFirtsClick = false;
            }
            if (useTimer && !isTimerStarted)
            {
                startTimer();
                isTimerStarted = true;
            }
            if(c.isClickable()){
                log(String.valueOf(c.getPosition()));
                c.openBlock();
                if (c.isMined())  gameOver("No, has perdido");
                if(checkWin()) gameOver("Si, has ganado");
            }else{
                System.out.println("No es clicable");
            }
        }
    }
    private void putBombs(Casilla casilla) {
        int numBombs = DesarrolloJuego.totalNumberOfMines;
        Casilla ca;
        casillas.get(0).setNumBombes(numBombs);
        casillas.get(0).updateBombs();
        System.out.println("numBombs"+numBombs);
        Random randomGenerator = new Random();
        int i =0;
        while (i<numBombs){
            int randomInt = randomGenerator.nextInt(casillas.size());
            ca = casillas.get(randomInt);
            if(!ca.isMined() && !ca.equals(casilla) ) {
                casillas.get(randomInt).setMined();
                i+=1;
            }
        }
        for (Casilla c: casillas){
            c.calculateCellsSurrounding();
        }
    }
    private boolean checkWin() {
        for (Casilla c:casillas){
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
        for (Casilla c:casillas){
            if(c.isMined() && c.isFlagged())  i+=1;
        }
        return i;
    }

    private int casillasCovered() {
        int i = 0;
        for (Casilla c:casillas){
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
            b.putString("alias", DesarrolloJuego.alias);
            b.putInt("bombas", DesarrolloJuego.totalNumberOfMines);
            b.putInt("casillas", DesarrolloJuego.numCasillas);
            b.putInt("bombasTotales", DesarrolloJuego.numBombs);
            b.putInt("porciento", DesarrolloJuego.porcientominas);
            b.putInt("tiempo", secondsPassed);
            b.putString("resultado", s);
            b.putInt("casillasRestantes", n);
            b.putInt("bombasRestantes", bo);
        }
        in.putExtras(b);
        a.startActivity(in);
        a.finish();
    }


    private class OnLongClickListener implements View.OnLongClickListener{
        Casilla c;

        private OnLongClickListener(Casilla c) {
            this.c = c;
        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) { //Estados casilla "vacio" "bandera" "?"
            fgpar.onItemClick(c,true);
            if(isFirtsClick){
                putBombs(c);
                isFirtsClick = false;
                System.out.println("isFirstClick");
            }
            if (useTimer && !isTimerStarted)
            {
                startTimer();
                isTimerStarted = true;
            }
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
            return true;
        }
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

