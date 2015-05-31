package com.example.especials.buscaminas;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private static Tablero tablero;
    public List<Casilla> casillas;

    public static Tablero getTablero(){
        if(tablero == null) tablero = new Tablero();
        return tablero;
    }

    private Tablero(){
        casillas  = new ArrayList<>();
    }

    public void clearTablero(){casillas.clear();}
}
