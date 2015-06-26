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
            String state = casilla.getContexto().getState().toReviewString();
            review += "casilla : " + casilla.getPosition() + " " + covered + " " + state + " " + casilla.getMinesInSurrounding()+ ";\n";
        }
        p.review = review;
        System.out.println(review);
    }

    public void showReview(){
        PartidaBuilder builder = new ReviewPartidaBuilder();

    }

    private void putBombs(){

    }
}
