package com.example.especials.buscaminas;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class QueryFrag extends Fragment {

    private ListView lstListado;
    private PartidasListener listener;

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(getActivity(), "DBPartidas", null, 1);
        db = usdbh.getReadableDatabase();



    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.queryfrag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        lstListado = (ListView)getView().findViewById(R.id.listViewQueryFrag);

        lstListado.setAdapter(new AdaptadorPartidas(this));

        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if(listener != null)
                    listener.onPartidaSeleccionada((Partida) lstListado.getAdapter().getItem(pos));

            }

        });
    }


    @Override
    public void onAttach(Activity ac) {
        super.onAttach(ac);
        try {

            listener = (PartidasListener) ac;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(ac.toString() + " must implement OnPartidasListener");
        }
    }


    class AdaptadorPartidas extends ArrayAdapter<Partida> {

        Activity context;

        AdaptadorPartidas(QueryFrag fragmentListado) {

            super(fragmentListado.getActivity(),R.layout.listitem_query);
            this.context = fragmentListado.getActivity();
        }

        @Override
        public Partida getItem(int position) {
            Cursor c = db.rawQuery("SELECT * FROM Partidas", null);
            c.moveToPosition(position); // retorna un boolean
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int i = 0;
            try {

                String alias = c.getString(i); i++;
                Date fecha = format.parse(c.getString(i)); i++;
                int numeroCasillas = c.getInt(i); i++;
                int numeroCasillasRestantes = c.getInt(i); i++;
                int porCientoMinas = c.getInt(i); i++;
                int tiempo = c.getInt(i); i++;
                String resultado = c.getString(i); i++;
                String bomba = c.getString(i); i++;
                return new Partida(alias, fecha, numeroCasillas, numeroCasillasRestantes, porCientoMinas, tiempo, resultado, bomba);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_query, null);

            TextView lblAlias = (TextView)item.findViewById(R.id.queryAlias);
            //lblAlias.setText(datos[position].getDe());

            TextView lblFechaHora = (TextView)item.findViewById(R.id.queryFechaHora);
            //lblFechaHora.setText(datos[position].getAsunto());

            TextView lblDetalle = (TextView)item.findViewById(R.id.queryFechaHora);
            //lblDetalle.setText(datos[position].getDetalle());


            return(item);
        }
    }

    public interface PartidasListener {
        void onPartidaSeleccionada(Partida c);
    }

    public void setCorreosListener(PartidasListener listener) {

        this.listener = listener;
    }
}
