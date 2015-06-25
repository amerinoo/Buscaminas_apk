package com.example.especials.buscaminas;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        adapter = new AdaptadorPartidas(this,"SELECT * FROM " + UsuariosSQLiteHelper.nameTable,null,db);
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


    public interface PartidasListener {
        void onPartidaSeleccionada(Partida p);
        void showAllLog(Partida p);
        void removeDetail();
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
        contextMenu.add(0, 1, 1, getString(R.string.action_eliminar));
        contextMenu.add(0, 2, 2, getString(R.string.action_mostrarTodos));
        contextMenu.add(0, 3, 3, getString(R.string.action_mostrarLogCompleto));
        contextMenu.add(0, 4, 4, getString(R.string.action_send));

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                remove(info.position);
                return true;
            case 2:
                showAllWithThisAlias(info.position);
                return true;
            case 3:
                showAllLog(info.position);
                return true;
            case 4:
                sendByEmail(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void sendByEmail(int position) {
        System.out.println("Entro al sendByEmail");
        Partida p = (Partida) lstListado.getAdapter().getItem(position);
        String email = putEmail();
        System.out.println(email);
        if (!email.trim().isEmpty()) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
            i.putExtra(Intent.EXTRA_SUBJECT, "Log - " + p.fecha);
            i.putExtra(Intent.EXTRA_TEXT, p.getAllLog());
            startActivity(Intent.createChooser(i, getString(R.string.seleccionarAplicacion)));
        }else showToast(getString(R.string.emailNecesario));

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
        adapter = new AdaptadorPartidas(this, "SELECT * FROM " + UsuariosSQLiteHelper.nameTable + " WHERE alias=?",args,db);
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
        String text = String.format(getString(R.string.partidasBorradas),partides);
        if(partides > 0) {
            listener.removeDetail();
            showToast(text);
        }else showToast(getString(R.string.errorEliminarElementos));
        adapter.notifyDataSetChanged();
    }
    private String putEmail() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return pref.getString("email", "correo@default.com");

    }
    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
