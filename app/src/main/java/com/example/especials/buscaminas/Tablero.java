package com.example.especials.buscaminas;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private static Tablero tablero;
    public List<Casilla> casillas;
    public Partida partida;
    public int numBombes;

    public static Tablero getTablero(){
        if(tablero == null) tablero = new Tablero();
        return tablero;
    }

    private Tablero(){
        casillas  = new ArrayList<>();
        partida = new Partida();
    }

    public void clearTablero(){casillas.clear(); partida = new Partida();numBombes = 0;}
}
