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
    }
}
