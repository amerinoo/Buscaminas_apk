package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class CasillaOpcion implements ReviewOption {
    private String toParse;

    public CasillaOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {
        String[] tokens = toParse.split(" ");
        int i = 0;
        int position = Integer.valueOf(tokens[i]); i++;
        boolean isCovered = Boolean.valueOf(tokens[i]); i++;
        String state = tokens[i]; i++;
        int numBombs = Integer.valueOf(tokens[i]);i++;
        partidaBuilder.getCasillas().add(new Casilla(position,isCovered,state,numBombs));
    }
}
