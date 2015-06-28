package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class BombState implements State {
    @Override
    public void doAction(Contexto contexto) {
        contexto.casilla.imThis.setBackgroundResource(R.drawable.crazy_bomb);
        contexto.setState(this);
    }

    @Override
    public String toReviewString() {
        return "b";
    }
}
