package com.example.especials.buscaminas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Especials on 30/05/2015.
 */
public class FragmentLog extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragmentlog, container, false);
        }

        public void mostrarDetalle(String texto) {


            TextView txtDetalle = (TextView) getView().findViewById(R.id.TxtLog);
            txtDetalle.setText(texto);
        }
}

