package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class AccesoBDActivity extends Activity implements QueryFrag.PartidasListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_bd);
        QueryFrag queryFrag = (QueryFrag) getFragmentManager().findFragmentById(R.id.fragmentQuery);
        queryFrag.setPartidasListener(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accesobd, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_limpiarBD:
                removeAllGames();
                return true;
            case android.R.id.home:
                goMainActivityBD(null);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void removeAllGames() {
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBPartidas", null, UsuariosSQLiteHelper.version);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        int a = db.delete(UsuariosSQLiteHelper.nameTable, null, null);
        String text = String.format(getString(R.string.partidasBorradas),a);
        if(a > 0) showToast(text);
        else showToast(getString(R.string.errorEliminarElementos));
        updateList(db);
    }

    private void updateList(SQLiteDatabase db) {
        AdaptadorPartidas adapter = new AdaptadorPartidas(this, "SELECT * FROM " + UsuariosSQLiteHelper.nameTable, null, db);
        ListView lstListado = (ListView) findViewById(R.id.listViewQueryFrag);
        lstListado.setAdapter(adapter);
        removeDetail();
        adapter.notifyDataSetChanged();

    }

    public void goMainActivityBD(View v){
        Intent in = new Intent(AccesoBDActivity.this,MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onPartidaSeleccionada(Partida p) {
        RegistroFrag registroFrag = (RegistroFrag) getFragmentManager().findFragmentById(R.id.fragmentRegistro);
        if (registroFrag != null && registroFrag.isInLayout()){
            registroFrag.mostrarRegistro(p.getLog());
        }else{
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_TEXTO,p.getLog());
            startActivity(i);
        }
    }

    @Override
    public void showAllLog(Partida p) {
        RegistroFrag registroFrag = (RegistroFrag) getFragmentManager().findFragmentById(R.id.fragmentRegistro);
        if (registroFrag != null && registroFrag.isInLayout()){
            registroFrag.mostrarRegistro(p.getAllLog());
        }else{
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_TEXTO,p.getAllLog());
            startActivity(i);
        }
    }

    @Override
    public void removeDetail() {
        RegistroFrag registroFrag = (RegistroFrag) getFragmentManager().findFragmentById(R.id.fragmentRegistro);
        if (registroFrag != null && registroFrag.isInLayout()) {
            registroFrag.mostrarRegistro("");
        }
    }

    @Override
    public void showReview(Partida p) {

    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
