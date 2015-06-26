package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class FlagState implements State {

    @Override
    public void doAction(Contexto contexto) {
        contexto.imThis.setBackgroundResource(R.drawable.prova);
        contexto.imThis.setClickable(false);
        contexto.setState(this);
    }

    @Override
    public String toReviewString() {
        return "f";
    }
}
