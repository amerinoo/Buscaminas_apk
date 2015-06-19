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
                new UsuariosSQLiteHelper(getActivity(), "DBPartidas", null, 2);
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
        public int getCount() {
            return db.rawQuery("SELECT * FROM Partidas", null).getCount();
        }

        @Override
        public Partida getItem(int position) {
            Cursor c = db.rawQuery("SELECT * FROM Partidas", null);
            c.moveToPosition(position); // retorna un boolean
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Comenca el try!!!");
            int i = 1;

                String alias = c.getString(i); i++;
                System.out.println("Alias " + alias);
                Date fecha = null;i++; //format.parse(c.getString(i)); i++;
                //System.out.println(fecha.toString());
                int numeroCasillas = c.getInt(i); i++;
                System.out.println(numeroCasillas);
                int numeroCasillasRestantes = c.getInt(i); i++;
                System.out.println(numeroCasillasRestantes);
                int porCientoMinas = c.getInt(i); i++;
                System.out.println(porCientoMinas);
                int tiempo = c.getInt(i); i++;
                System.out.println(tiempo);
                String resultado = c.getString(i); i++;
                System.out.println(resultado);
                String bomba = c.getString(i); i++;
                System.out.println(bomba);
                return new Partida(alias, fecha, numeroCasillas, numeroCasillasRestantes, porCientoMinas, tiempo, resultado, bomba);


        }



        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_query, null);
            Partida p = getItem(position);
            TextView lblAlias = (TextView)item.findViewById(R.id.queryAlias);
            lblAlias.setText(p.alias);

            TextView lblFechaHora = (TextView)item.findViewById(R.id.queryFechaHora);
            //lblFechaHora.setText(p.fecha.toString());

            TextView lblDetalle = (TextView)item.findViewById(R.id.queryDetalle);
            lblDetalle.setText(p.resultado);


            return(item);
        }
    }

    public interface PartidasListener {
        void onPartidaSeleccionada(Partida c);
    }

    public void setPartidasListener(PartidasListener listener) {

        this.listener = listener;
    }
}
