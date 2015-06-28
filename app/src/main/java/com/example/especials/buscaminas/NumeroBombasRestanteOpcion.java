package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class NumeroBombasRestanteOpcion implements ReviewOption {
    private String toParse;

    public NumeroBombasRestanteOpcion(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public void toBuilder(PartidaBuilder partidaBuilder) {
        partidaBuilder.setNumeroBombasRestantes(Integer.valueOf(toParse));
    }
}
