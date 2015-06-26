package com.example.especials.buscaminas;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by pestomerdes on 6/25/15.
 */
public class AdaptadorPartidas extends ArrayAdapter<Partida> {

    Activity context;
    String rawQuery;
    String[] args;
    private SQLiteDatabase db;

    AdaptadorPartidas(QueryFrag fragmentListado,String rawQuery, String[] args, SQLiteDatabase db) {

        super(fragmentListado.getActivity(), R.layout.listitem_query);
        inicialize(rawQuery,args,db);
        this.context = fragmentListado.getActivity();

    }
    AdaptadorPartidas(AccesoBDActivity context,String rawQuery, String[] args, SQLiteDatabase db) {

        super(context, R.layout.listitem_query);
        inicialize(rawQuery,args,db);
        this.context = context;

    }

    private void inicialize(String rawQuery, String[] args, SQLiteDatabase db) {
        this.rawQuery = rawQuery;
        this.args = args;
        this.db = db;
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
        return new Partida(alias, fecha, numeroCasillas, numeroCasillasRestantes, porCientoMinas, tiempo, resultado, bomba, allLog,getContext());


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
