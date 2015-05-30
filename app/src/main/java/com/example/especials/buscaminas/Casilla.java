package com.example.especials.buscaminas;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.especials.buscaminas.DesarrolloJuego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Especials on 27/03/2015.
 */
public class Casilla {

    private final int bombStyle;
    private boolean isCovered; // is block covered yet
    private boolean isMined; // does the block has a mine underneath
    private boolean isFlagged; // is block flagged as a potential mine
    private boolean isQuestionMarked; // is block question marked
    private boolean isClickable; // can block accept click events
    private int numberOfMinesInSurrounding; // number of mines in nearby blocks
    private List<Casilla> minesInSurrounding;
    private int position=-1;
    private Button imThis;
    private List<Casilla> casillas;
    private static int numBombes;

    private int numberOfColumnsInMineField;
    private int numCasillas;


    public Casilla(int bombStyle) {
        this.isMined = false;
        this.bombStyle = bombStyle;
        this.casillas = DesarrolloJuego.casillas;
        numberOfColumnsInMineField = DesarrolloJuego.numberOfColumnsInMineField;
        numCasillas = DesarrolloJuego.numCasillas;

        minesInSurrounding = new ArrayList<>();
        setDefaults();

    }

    // set default properties for the block
    public void setDefaults()
    {
        isCovered = true;
        isFlagged = false;
        isQuestionMarked = false;
        isClickable = true;
    }

    public void calculateCellsSurrounding() {
        try {
            int pos;

                //N
                //System.out.println("N " + position);
                pos = position - numberOfColumnsInMineField;
                //System.out.println("Soc una mina " + position);
                if (pos >= 0) putCellSurrounding(pos);
                //NO
                //System.out.println("NO " + position);
                pos = position - numberOfColumnsInMineField - 1;
                if (pos >= 0 && (pos+1)%numberOfColumnsInMineField!=0) putCellSurrounding(pos);
                //NE
                //System.out.println("NE " + position);
                pos = position - numberOfColumnsInMineField + 1;
                if (pos >= 0 && pos%numberOfColumnsInMineField!=0) putCellSurrounding(pos);
                //S
                //System.out.println("S " + position);
                pos = position + numberOfColumnsInMineField;
                if (pos < numCasillas) putCellSurrounding(pos);
                //SO
                //System.out.println("SO " + position);
                pos = position + numberOfColumnsInMineField - 1;
                if (pos < numCasillas && (pos+1)%numberOfColumnsInMineField!=0) putCellSurrounding(pos);
                //SE
                //System.out.println("SE " + position);
                pos = position + numberOfColumnsInMineField + 1;
                if (pos < numCasillas && pos%numberOfColumnsInMineField!=0) putCellSurrounding(pos);
                //O
                //System.out.println("O " + position);
                pos = position - 1;
                if (position % numberOfColumnsInMineField != 0)
                    putCellSurrounding(pos);
                //E
                //System.out.println("E " + position);
                pos = position + 1;
                if ((position + 1) % numberOfColumnsInMineField != 0)
                    putCellSurrounding(pos);

        }catch (Exception e){
            System.out.println("he petat mirant la pos ");
        }

    }

    private void putCellSurrounding(int pos){
        Casilla casilla =  casillas.get(pos);
        if(isMined)casilla.addMineInSurrounding();
        minesInSurrounding.add(casilla);
    }

    // set block as disabled/opened if true is passed
    // else enable/close it
    public void setBlockAsDisabled(boolean disabled)
    {
        if (disabled) imThis.setBackgroundResource(R.drawable.cuadrado_gris);
        else imThis.setBackgroundResource(R.drawable.cuadrado_azul);
    }

    // uncover this block
    public void openBlock()
    {

        // cannot uncover a mine which is not covered
        if (!isCovered)
            return;
        if (isMined){
            showToast("BUUUUUUUUUUUUUM");
        }
        setBlockAsDisabled(true);
        imThis.setClickable(false);
        setCovered(false);
        if(isMined()) imThis.setBackgroundResource(bombStyle);
        else setTextMinasSurrounding(numberOfMinesInSurrounding);
        destaparCeldasSinBombas();

        System.out.println("He obert el block de la posicio: " + position + " " + numberOfMinesInSurrounding);

    }

    private void setTextMinasSurrounding(int minas) {
        if(minas!=0)imThis.setText(String.valueOf(numberOfMinesInSurrounding));
        imThis.setTextSize(30);
        switch (minas){
            case 1:
                imThis.setTextColor(Color.CYAN);
                break;
            case 2:
                imThis.setTextColor(Color.GREEN);
                break;
            case 3:
                imThis.setTextColor(Color.RED);
                break;
            case 4:
                imThis.setTextColor(Color.BLUE);
                break;

        }
    }


    public void updateBombs() {
        String message = String.format(DesarrolloJuego.context.getString(R.string.infoBombes), numBombes);
        DesarrolloJuego.textView.setText(message);
    }
    private void destaparCeldasSinBombas() {
        if(numberOfMinesInSurrounding == 0 && !isMined && !isFlagged){
            System.out.println("Casilla " + position + " " + minesInSurrounding);
            for(Casilla c: minesInSurrounding)
                try {
                    if (!c.isFlagged() && !c.isQuestionMarked()) c.openBlock();
                }catch (Exception e){
                    System.out.println(c);
                }
        }
    }
    public void putMinesInSurrounding(){
        setBlockAsDisabled(true);
       setTextMinasSurrounding(numberOfMinesInSurrounding);
    }

    public void clean(){
        imThis.setText("");
        setQuestionMarked(false);
        imThis.setClickable(true);

    }

    public void putFlag(){
        imThis.setBackgroundResource(R.drawable.prova);
        if (!isFlagged)numBombes-=1;
        setFlagged(true);
        imThis.setClickable(false);

        updateBombs();
    }

    public void putQuestionMarked(){
        setBlockAsDisabled(false);
        imThis.setText("?");
        if ((numBombes > 0 || isFlagged) && !isQuestionMarked) numBombes+=1;
        setFlagged(false);
        setQuestionMarked(true);

        updateBombs();
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean isCovered) {
        this.isCovered = isCovered;
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined() { this.isMined = true; }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) { this.isFlagged = isFlagged;  }

    public boolean isQuestionMarked() {
        return isQuestionMarked;
    }

    public void setQuestionMarked(boolean isQuestionMarked) {
        this.isQuestionMarked = isQuestionMarked;
    }
    public int getNumBombes(){ return numBombes;}
    public void setNumBombes(int numBombes){
        this.numBombes = numBombes;
    }
    public int getNumberOfMinesInSurrounding() {
        return numberOfMinesInSurrounding;
    }

    public void addMineInSurrounding() {
        this.numberOfMinesInSurrounding += 1;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setButton(Button b){imThis = b;}

    @Override
    public String toString() {
        return "Casilla{" +
                "position=" + position +
                '}';
    }

    public boolean isClickable() {
        return imThis.isClickable();
    }
    private void showToast(String text) {
        Toast.makeText(DesarrolloJuego.context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casilla)) return false;

        Casilla casilla = (Casilla) o;

        if (bombStyle != casilla.bombStyle) return false;
        if (isClickable != casilla.isClickable) return false;
        if (isCovered != casilla.isCovered) return false;
        if (isFlagged != casilla.isFlagged) return false;
        if (isMined != casilla.isMined) return false;
        if (isQuestionMarked != casilla.isQuestionMarked) return false;
        if (numCasillas != casilla.numCasillas) return false;
        if (numberOfColumnsInMineField != casilla.numberOfColumnsInMineField) return false;
        if (numberOfMinesInSurrounding != casilla.numberOfMinesInSurrounding) return false;
        if (position != casilla.position) return false;
        if (casillas != null ? !casillas.equals(casilla.casillas) : casilla.casillas != null)
            return false;
        if (imThis != null ? !imThis.equals(casilla.imThis) : casilla.imThis != null) return false;
        if (minesInSurrounding != null ? !minesInSurrounding.equals(casilla.minesInSurrounding) : casilla.minesInSurrounding != null)
            return false;

        return true;
    }

}
