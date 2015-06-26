package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class QuestionMarkedState implements State {
    @Override
    public void doAction(Contexto contexto) {
        contexto.imThis.setText("?");
        contexto.setState(this);
    }

    @Override
    public String toReviewString() {
        return "?";
    }
}
