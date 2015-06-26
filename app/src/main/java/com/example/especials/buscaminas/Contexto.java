package com.example.especials.buscaminas;

import android.widget.Button;

/**
 * Created by pestomerdes on 6/26/15.
 */
public class Contexto {
    State state;
    Button imThis;

    public Contexto(Button button){
        state = null;
        imThis = button;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
