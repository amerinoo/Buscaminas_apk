package com.example.especials.buscaminas;

/**
 * Created by Gary on 28/06/2015.
 */
public class OpenState implements State {
    @Override
    public void doAction(Contexto contexto) {
        contexto.casilla.imThis.setText(String.valueOf(contexto.casilla.getMinesInSurrounding()));
        contexto.setState(this);
    }

    @Override
    public String toReviewString() {
        return "o";
    }
}
