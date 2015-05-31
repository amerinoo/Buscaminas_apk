package com.example.especials.buscaminas;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentParrilla extends Fragment {


    private CasillaListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentparrilla, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }

    public void onItemClick(Casilla c, boolean longClick) {
        if(listener != null)
            listener.onCasillaSeleccionada(c,longClick);

    }

    @Override
    public void onAttach(Activity ac) {
        super.onAttach(ac);
        try {

            listener = (CasillaListener) ac;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(ac.toString() + " must implement CasillaListener");
        }
    }




    public interface CasillaListener {
        void onCasillaSeleccionada(Casilla c, boolean longClick);
    }

    public void setCasillaListener(CasillaListener listener) {

        this.listener = listener;
    }
}
