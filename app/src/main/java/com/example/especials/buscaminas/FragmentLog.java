package com.example.especials.buscaminas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentLog extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmentlog, container, false);
    }

    public void log(String text){

        TextView tv = ((TextView) getView().findViewById(R.id.TxtLog));
        tv.setText(tv.getText().toString() + text);

    }
}

