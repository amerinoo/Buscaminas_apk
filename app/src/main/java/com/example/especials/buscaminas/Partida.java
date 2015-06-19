package com.example.especials.buscaminas;

import java.util.Date;

/**
 * Created by Especials on 12/06/2015.
 */
public class Partida {
    public String alias;
    public Date fecha;
    public int numeroCasillas;
    public int numeroCasillasRestantes;
    public int porCientoMinas;
    public int tiempo;
    public String resultado;
    public String bomba;
    private String log = "";


    public Partida(String alias, int porCientoMinas, int numeroCasillas) {
        this.alias = alias;
        this.porCientoMinas = porCientoMinas;
        this.numeroCasillas = numeroCasillas;
    }

    public Partida(String alias, Date fecha, int numeroCasillas, int numeroCasillasRestantes, int porCientoMinas, int tiempo, String resultado, String bomba) {
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

    private void doLogDB() {
        setLog("Alias - " + alias + "\n");
        setLog("Fecha - " + fecha + "\n");
        setLog("#Casillas - " + numeroCasillas + "\n");
        setLog("#Casillas restantes" + numeroCasillasRestantes + "\n");
        setLog("% Minas - " + porCientoMinas + "\n");
        setLog("Resultado - " + resultado + "\n");
        setLog("Bomba - " + bomba + "\n");
    }

    public String getLog(){
        return log;
    }

    public void setLog(String text){log += text;}
}
