package com.example.especials.buscaminas;

import android.widget.Button;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class Contexto {
    State state;
    Casilla casilla;

    public Contexto(Casilla casilla){
        state = null;
        this.casilla = casilla;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
