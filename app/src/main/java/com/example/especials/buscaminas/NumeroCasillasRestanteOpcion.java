package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class NumeroCasillasRestanteOpcion implements ReviewOption {
    private String toParse;

    public NumeroCasillasRestanteOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {
        partidaBuilder.setNumeroCasillasRestantes(Integer.valueOf(toParse));
    }
}
