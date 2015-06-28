package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class ReviewPartidaBuilder extends PartidaBuilder {
    @Override
    public void build() {
        Partida p = Tablero.getTablero().partida;
        p.numeroCasillas = getNumeroCasillas();
        p.tiempo = getTiempo();
        p.numeroCasillasRestantes = getNumeroCasillasRestantes();
        p.allLog = getAllLog();
        p.numBombas = getNumeroBombasRestantes();
        Tablero.getTablero().casillas = getCasillas();
        Tablero.getTablero().numBombes = getNumeroBombasRestantes();
    }
}
