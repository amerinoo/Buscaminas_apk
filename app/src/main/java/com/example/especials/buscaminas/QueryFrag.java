package com.example.especials.buscaminas;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    AdaptadorPartidas adapter;

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(getActivity(), "DBPartidas", null, UsuariosSQLiteHelper.version);
        db = usdbh.getWritableDatabase();



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
        adapter = new AdaptadorPartidas(this,"SELECT * FROM " + UsuariosSQLiteHelper.nameTable,null);
        lstListado.setAdapter(adapter);
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if(listener != null)
                    listener.onPartidaSeleccionada((Partida) lstListado.getAdapter().getItem(pos));

            }

        });
        lstListado.setOnCreateContextMenuListener(this);
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
        String rawQuery;
        String[] args;

        AdaptadorPartidas(QueryFrag fragmentListado,String rawQuery, String[] args) {

            super(fragmentListado.getActivity(),R.layout.listitem_query);
            this.context = fragmentListado.getActivity();
            this.rawQuery = rawQuery;
            this.args = args;

        }

        @Override
        public int getCount() {
            return db.rawQuery(rawQuery, args).getCount();
        }

        @Override
        public Partida getItem(int position) {
            Cursor c = db.rawQuery(rawQuery, args);
            c.moveToPosition(position); // retorna un boolean
            int i = 1;

            String alias = c.getString(i); i++;
            String fecha = c.getString(i); i++;
            int numeroCasillas = c.getInt(i); i++;
            int numeroCasillasRestantes = c.getInt(i); i++;
            int porCientoMinas = c.getInt(i); i++;
            int tiempo = c.getInt(i); i++;
            String resultado = c.getString(i); i++;
            String bomba = c.getString(i); i++;
            String allLog = c.getString(i);
            return new Partida(alias, fecha, numeroCasillas, numeroCasillasRestantes, porCientoMinas, tiempo, resultado, bomba, allLog);


        }



        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_query, null);
            Partida p = getItem(position);
            TextView lblAlias = (TextView)item.findViewById(R.id.queryAlias);
            lblAlias.setText(p.alias);

            TextView lblFechaHora = (TextView)item.findViewById(R.id.queryFechaHora);
            lblFechaHora.setText(p.fecha);

            TextView lblDetalle = (TextView)item.findViewById(R.id.queryDetalle);
            lblDetalle.setText(p.resultado);


            return(item);
        }
    }

    public interface PartidasListener {
        void onPartidaSeleccionada(Partida p);
        void showAllLog(Partida p);
    }

    public void setPartidasListener(PartidasListener listener) {

        this.listener = listener;
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        String title = ((TextView)info.targetView.findViewById(R.id.queryAlias)).getText().toString();
        title += " " + ((TextView)info.targetView.findViewById(R.id.queryFechaHora)).getText().toString();
        contextMenu.setHeaderTitle(title);
        contextMenu.add(0, 0, 0, "Edit");
        contextMenu.add(0, 1, 1, "Delete");
        contextMenu.add(0, 2, 2, "Show All With This Alias");
        contextMenu.add(0, 3, 3, "Show All Log");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        System.out.println("Menu contextual " + item.getItemId());
        switch (item.getItemId()) {
            case 0:
                return true;
            case 1:
                remove(info.position);
                return true;
            case 2:
                showAllWithThisAlias(info.position);
                return true;
            case 3:
                showAllLog(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showAllLog(int position) {
        System.out.println("Entro al showAllLog");
        Partida p = (Partida) lstListado.getAdapter().getItem(position);
        listener.showAllLog(p);
    }

    private void showAllWithThisAlias(int position) {
        System.out.println("Entro al showAll");
        Partida p = (Partida) lstListado.getAdapter().getItem(position);

        String alias = p.alias;
        String[] args = new String[]{alias};
        adapter = new AdaptadorPartidas(this, "SELECT * FROM " + UsuariosSQLiteHelper.nameTable + " WHERE alias=?",args);
        lstListado.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void remove(int position) {
        System.out.println("Entro al remove");
        Partida p = (Partida) lstListado.getAdapter().getItem(position);

        String alias = p.alias;
        String fecha = p.fecha;
        System.out.println(alias + " " + fecha);

        int partides = db.delete(UsuariosSQLiteHelper.nameTable, "alias=? AND fecha=?", new String[]{alias,fecha});
        System.out.println(partides);
        adapter.notifyDataSetChanged();
    }
}
