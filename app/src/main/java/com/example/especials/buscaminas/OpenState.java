package com.example.especials.buscaminas;

import android.graphics.Color;
import android.widget.Button;

/**
 * Created by Gary on 28/06/2015.
 */
public class OpenState implements State {
    @Override
    public void doAction(Contexto contexto) {
        contexto.casilla.setBlockAsDisabled(true);
        setTextMinasSurrounding(contexto.casilla.getMinesInSurrounding(),contexto.casilla.imThis);
        contexto.setState(this);
    }
    private void setTextMinasSurrounding(int minas, Button imThis) {
        if(minas!=0)imThis.setText(String.valueOf(minas));
        imThis.setTextSize(30);
        switch (minas){
            case 1:
                imThis.setTextColor(Color.CYAN);
                break;
            case 2:
                imThis.setTextColor(Color.GREEN);
                break;
            case 3:
                imThis.setTextColor(Color.RED);
                break;
            case 4:
                imThis.setTextColor(Color.BLUE);
                break;

        }
    }
    @Override
    public String toReviewString() {
        return "o";
    }
}
