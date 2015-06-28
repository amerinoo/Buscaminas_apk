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
        review += "nc : " + p.numeroCasillas + ";\n";
        review += "ncr : " + p.numeroCasillasRestantes + ";\n";
        review += "t : " + p.tiempo + ";\n";
        review += "allLog : " + p.getLog() + ";\n";
        for(Casilla casilla : Tablero.getTablero().casillas){
            int covered = (casilla.isCovered()) ? 0 : 1;
            String state = casilla.getContexto().getState().toReviewString();
            review += "casilla : " + casilla.getPosition() + " " + covered + " " + state + " " + casilla.getMinesInSurrounding()+ ";\n";
        }
        p.review = review;
        System.out.println(review);
    }

    public void showReview(){
        String s = "nc&4;ncr&4;t&0;allLog&Alias patata Casillas: 25 %Minas: 25% Minas: 6\nCasilla (0, 0) abierta\nDerrota;casilla&0 0 - 0;" +
                "casilla&1 0 - 0;" +
                "casilla&2 0 - 0;" +
                "casilla&3 0 - 0;";
        Tablero.getTablero().partida = p;
        PartidaBuilder builder = new ReviewPartidaBuilder();
        new PartidaParser(builder).parse(s);
        builder.build();
    }

}
