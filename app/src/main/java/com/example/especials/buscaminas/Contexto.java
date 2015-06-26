package com.example.especials.buscaminas;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class Contexto {
    State state;
    public Contexto(){
        state = null;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
