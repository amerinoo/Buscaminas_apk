package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class TiempoOpcion implements ReviewOption {
    private String toParse;

    public TiempoOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {

    }
}
