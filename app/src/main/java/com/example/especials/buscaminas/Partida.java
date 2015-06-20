package com.example.especials.buscaminas;

import java.util.Date;

/**
 * Created by Especials on 12/06/2015.
 */
public class Partida {
    public String alias = null;
    public String fecha = null;
    public int numeroCasillas = -1;
    public int numeroBanderasOK = -1;
    public int porCientoMinas = -1;
    public int tiempo = 0;
    public String resultado = null;
    public String bomba = null;
    public int numBombas = -1;
    private String log = "";
    public int numeroCasillasRestantes = -1;

    public Partida() {}

    public Partida(String alias, String fecha, int numeroCasillas, int numeroCasillasRestantes, int porCientoMinas, int tiempo, String resultado, String bomba) {
        this.alias = alias;
        this.fecha = fecha;
        this.numeroCasillas = numeroCasillas;
        this.numeroCasillasRestantes = numeroCasillasRestantes;
        this.porCientoMinas = porCientoMinas;
        this.tiempo = tiempo;
        this.resultado = resultado;
        this.bomba = bomba;
        doLogDB();

    }

    public Partida(Partida p){
        this.alias = p.alias;
        this.fecha = p.fecha;
        this.numeroCasillas = p.numeroCasillas;
        this.numeroBanderasOK = p.numeroBanderasOK;
        this.porCientoMinas = p.porCientoMinas;
        this.tiempo = p.tiempo;
        this.resultado = p.resultado;
        this.bomba = p.bomba;
        this.log = p.getLog();
        this.numBombas = p.numBombas;
        this.numeroCasillasRestantes = p.numeroCasillasRestantes;

    }

    private void doLogDB() {
        setToLog("Alias - " + alias + "\n");
        setToLog("Fecha - " + fecha + "\n");
        setToLog("#Casillas - " + numeroCasillas + "\n");
        setToLog("#Casillas restantes - " + numeroCasillasRestantes + "\n");
        setToLog("% Minas - " + porCientoMinas + "%\n");
        setToLog("Resultado - " + resultado + "\n");
        if (bomba != null)
            setToLog("Bomba - " + bomba + "\n");
    }

    public String getLog(){
        return log;
    }

    public void setToLog(String text){log += text;}

    public String tiempoToString() {
        return (tiempo == 0) ? "No usado" : String.valueOf(tiempo) + " segundos";
    }
}
