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
    public Casilla bomba;


    public Partida(String alias, int porCientoMinas, int numeroCasillas) {
        this.alias = alias;
        this.porCientoMinas = porCientoMinas;
        this.numeroCasillas = numeroCasillas;
    }
}
