package com.example.especials.buscaminas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pestomerdes on 6/26/15.
 */
public abstract class PartidaBuilder {
    public String allLog;
    public int numeroCasillas;
    public int numeroCasillasRestantes;
    public int tiempo;
    public List<Casilla> casillas = new ArrayList<>();
    public int numBombes;

    public abstract void build();

    //Getters

    public String getAllLog() {
        return allLog;
    }

    public int getNumeroCasillas() {
        return numeroCasillas;
    }

    public int getNumeroCasillasRestantes() {
        return numeroCasillasRestantes;
    }

    public int getTiempo() {
        return tiempo;
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public int getNumBombes() {
        return numBombes;
    }


    //Setters

    public void setAllLog(String allLog) {
        this.allLog = allLog;
    }

    public void setNumeroCasillas(int numeroCasillas) {
        this.numeroCasillas = numeroCasillas;
    }

    public void setNumeroCasillasRestantes(int numeroCasillasRestantes) {
        this.numeroCasillasRestantes = numeroCasillasRestantes;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setCasillas(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public void setNumBombes(int numBombes) {
        this.numBombes = numBombes;
    }
}
