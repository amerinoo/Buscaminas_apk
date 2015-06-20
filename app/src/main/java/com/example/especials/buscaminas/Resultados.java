package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Resultados extends Activity {
    private Partida p;
    private String log;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        putEmail();
        if(savedInstanceState == null) {
            Tablero tablero = Tablero.getTablero();
            p = new Partida(tablero.partida);
            tablero.clearTablero();
            p.fecha = getFecha();
            fecha = p.fecha;
            Bundle b = getIntent().getExtras();
            boolean smartphone = b.getBoolean("smartphone");
            int bo = b.getInt("banderasOK");
            ((TextView) findViewById(R.id.diaYHora)).setText(fecha);
            if (smartphone) // Smartphone
                log = getString(R.string.log_resultadosPartida)
                        + "\n\t" + getString(R.string.log_alias) + " " + p.alias
                        + "\n\t" + getString(R.string.log_casillas)+ " " + String.valueOf(p.numeroCasillas) + " "+ getString(R.string.log_abiertas) +" " + (p.numeroBanderasOK)
                        + "\n\t" + getString(R.string.log_porcientominas) + " " + String.valueOf(p.porCientoMinas) + "% "+ getString(R.string.log_banderasOK) + " " + bo
                        + "\n\t" + getString(R.string.log_tiempo) + " " + p.tiempoToString()
                        + "\n\t" + getString(R.string.log_esVictoria) + " " + p.resultado;
            else log = p.getLog();
            ((TextView) findViewById(R.id.log)).setText(log);
            toDB(); // Només ho s'ha de guardar un cop
        }else{
            log = savedInstanceState.getString("log");
            ((TextView) findViewById(R.id.log)).setText(log);
            fecha = savedInstanceState.getString("fecha");
            ((TextView) findViewById(R.id.diaYHora)).setText(fecha);
        }

    }

    private void putEmail() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        ((EditText)findViewById(R.id.editText)).setText(pref.getString("email", "correo@default.com"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("log",log);
        outState.putString("fecha",fecha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resultados, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                goPreferencias();
                return true;
            case R.id.action_send:
                sendEmail(null);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void goPreferencias() {
        Intent in = new Intent(Resultados.this,Preferencias.class);
        startActivity(in);
    }

    private void toDB() {
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBPartidas", null, UsuariosSQLiteHelper.version);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Insertamos los datos en la tabla Partidas
        try {
            db.execSQL("INSERT INTO Partidas (alias, fecha, numeroCasillas, numeroCasillasRestantes,porCientoMinas, tiempo, resultado, bomba, log) " +
                    "VALUES ('" + p.alias + "', '" + p.fecha + "', '" + p.numeroCasillas + "', '" + p.numeroCasillasRestantes + "', '" + p.porCientoMinas + "', '" + p.tiempo + "', '" + p.resultado + "', '" + p.bomba + "', '" + p.getLog() + "')");
            showToast(getString(R.string.paridaGuardada));
        }catch (Exception e){
            showToast(getString(R.string.paridaNoGuardada));
        }
        db.close();
    }

    private String getFecha(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(d);
    }

    public void goNuevaPartida(View v){
        Intent in = new Intent(Resultados.this,DesarrolloJuego.class);
        startActivity(in);
        finish();
    }

    public void sendEmail(View v){
        String email = ((EditText)findViewById(R.id.editText)).getText().toString();
        System.out.println(email);
        if (!email.trim().isEmpty()) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
            i.putExtra(Intent.EXTRA_SUBJECT, "Log - " + p.fecha);
            i.putExtra(Intent.EXTRA_TEXT, p.getLog());
            startActivity(Intent.createChooser(i, getString(R.string.seleccionarAplicacion)));
        }else showToast(getString(R.string.emailNecesario));
    }

    public void goSalirResultados(View v){
        finish();
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
