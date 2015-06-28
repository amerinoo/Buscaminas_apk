package com.example.especials.buscaminas;

import android.content.Context;
import android.content.res.Resources;

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
    public String allLog = "";
    public String review;
    public int numBombasRestantes = -1;

    private Context context;

    public Partida() {}

    public Partida(String alias, String fecha, int numeroCasillas, int numeroCasillasRestantes, int porCientoMinas, int tiempo, String resultado, String bomba, String allLog, String review, Context context) {
        this.alias = alias;
        this.fecha = fecha;
        this.numeroCasillas = numeroCasillas;
        this.numeroCasillasRestantes = numeroCasillasRestantes;
        this.porCientoMinas = porCientoMinas;
        this.tiempo = tiempo;
        this.resultado = resultado;
        this.bomba = bomba;
        this.allLog = allLog;
        this.review = review;
        this.context = context;
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
        this.numBombasRestantes = p.numBombasRestantes;

    }

    private void doLogDB() {
        setToLog(context.getString(R.string.log_partida_alias) + " - " + alias + "\n");

        setToLog(context.getString(R.string.log_partida_fecha) + " - " + fecha + "\n");
        setToLog(context.getString(R.string.log_partida_casillas) + " - " + numeroCasillas + "\n");
        setToLog(context.getString(R.string.log_partida_casillasRestantes) + " - " + numeroCasillasRestantes + "\n");
        setToLog(context.getString(R.string.log_partida_porcientoMinas) + " - " + porCientoMinas + "%\n");
        setToLog(context.getString(R.string.log_partida_tiempo) + " - " + tiempoToString() + "\n");
        setToLog(context.getString(R.string.log_partida_resultado) + " - " + resultado + "\n");
        if (resultado.equals(context.getString(R.string.log_derrota)))
            setToLog(context.getString(R.string.log_partida_bomba) + " - " + bomba + "\n");

    }

    public String getLog(){
        return log;
    }

    public void setToLog(String text){log += text;}

    public String tiempoToString() {
        return (tiempo == 0) ? context.getString(R.string.log_partida_tiempoNoUsado) : String.valueOf(tiempo) + " " + context.getString(R.string.log_partida_segundos);
    }

    public String getAllLog() {
        return allLog;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "alias='" + alias + '\'' +
                ", fecha='" + fecha + '\'' +
                ", numeroCasillas=" + numeroCasillas +
                ", numeroBanderasOK=" + numeroBanderasOK +
                ", porCientoMinas=" + porCientoMinas +
                ", tiempo=" + tiempo +
                ", resultado='" + resultado + '\'' +
                ", bomba='" + bomba + '\'' +
                ", numBombas=" + numBombas +
                ", log='" + log + '\'' +
                ", numeroCasillasRestantes=" + numeroCasillasRestantes +
                ", allLog='" + allLog + '\'' +
                '}';
    }
}
