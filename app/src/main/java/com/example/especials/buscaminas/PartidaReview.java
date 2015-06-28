package com.example.especials.buscaminas;

import java.util.List;

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
        review += "nc&" + p.numeroCasillas + ";";
        review += "ncr&" + p.numeroCasillasRestantes + ";";
        review += "nbr&" + p.numBombasRestantes + ";";
        review += "t&" + p.tiempo + ";";
        review += "allLog&" + p.getLog() + ";";
        for(Casilla casilla : Tablero.getTablero().casillas){
            int covered = (casilla.isCovered()) ? 0 : 1;
            String state = casilla.getContexto().getState().toReviewString();
            review += "casilla&" + casilla.getPosition() + " " + covered + " " + state + " " + casilla.getMinesInSurrounding()+ ";";
        }
        p.review = review;
        System.out.println(review);
    }

    public void showReview(){
        String s = p.review;
        Tablero.getTablero().partida = p;
        PartidaBuilder builder = new ReviewPartidaBuilder();
        new PartidaParser(builder).parse(s);
        builder.build();
    }

}
