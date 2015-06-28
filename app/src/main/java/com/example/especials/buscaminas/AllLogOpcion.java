package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class AllLogOpcion implements ReviewOption {
    private String toParse;

    public AllLogOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {

    }
}
