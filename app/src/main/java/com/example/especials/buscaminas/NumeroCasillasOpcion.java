package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class NumeroCasillasOpcion implements ReviewOption {
    private String toParse;

    public NumeroCasillasOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {
        partidaBuilder.setNumeroCasillas(Integer.valueOf(toParse));
    }
}
