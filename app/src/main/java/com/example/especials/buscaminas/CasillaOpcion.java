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

    }
}
