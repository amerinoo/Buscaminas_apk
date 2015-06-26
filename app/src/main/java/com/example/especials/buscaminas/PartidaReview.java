package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class PartidaReview implements Review {
    private Partida p;

    public PartidaReview(Partida p) {
        this.p = p;
    }

    public void makeReview(){
        String review = "";
        review += "nc : " + p.numeroCasillas + ";\n";
        review += "ncr : " + p.numeroCasillasRestantes + ";\n";
        review += "t : " + p.tiempo + ";\n";
        review += "allLog : " + p.allLog + ";\n";
        for(Casilla casilla : Tablero.getTablero().casillas){
            int covered = (casilla.isCovered()) ? 0 : 1;
            review += "casilla : " + casilla.getPosition() + " " + covered + " " + "state" + ";\n";
        }
        p.review = review;
    }

    public void showReview(){

    }

    private void putBombs(){

    }
}
