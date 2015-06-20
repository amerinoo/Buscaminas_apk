package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Resultados extends Activity {
    private Partida p;
    private String log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        if(savedInstanceState == null) {
            Tablero tablero = Tablero.getTablero();
            p = new Partida(tablero.partida);
            tablero.clearTablero();
            p.fecha = getFecha();
            Bundle b = getIntent().getExtras();
            boolean smartphone = b.getBoolean("smartphone");
            int bo = b.getInt("banderasOK");
            ((TextView) findViewById(R.id.diaYHora)).setText(p.fecha);
            if (smartphone) // Smartphone
                log = "Resultados partida:"
                        + "\n\tAlias: " + p.alias
                        + "\n\tCasillas: " + String.valueOf(p.numeroCasillas) + " Abiertas: " + (p.numeroBanderasOK)
                        + "\n\t% Minas: " + String.valueOf(p.porCientoMinas) + "% Banderas OK: " + bo
                        + "\n\tTiempo: " + p.tiempoToString()
                        + "\n\tVictoria? " + p.resultado;
            else log = p.getLog();
            ((TextView) findViewById(R.id.log)).setText(log);
            toDB(); // Només ho s'ha de guardar un cop
        }else{
            log = savedInstanceState.getString("log");
            ((TextView) findViewById(R.id.log)).setText(log);
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("log",log);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (featureId){
            case 0:
                goPreferencias();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void goPreferencias() {
        Intent in = new Intent(Resultados.this,Preferencias.class);
        startActivity(in);
        finish();
    }

    private void toDB() {
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBPartidas", null, UsuariosSQLiteHelper.version);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Insertamos los datos en la tabla Clientes
        try {
            db.execSQL("INSERT INTO Partidas (alias, fecha, numeroCasillas, numeroCasillasRestantes,porCientoMinas, tiempo, resultado, bomba, log) " +
                    "VALUES ('" + p.alias + "', '" + p.fecha + "', '" + p.numeroCasillas + "', '" + p.numeroCasillasRestantes + "', '" + p.porCientoMinas + "', '" + p.tiempo + "', '" + p.resultado + "', '" + p.bomba + "', '" + p.getLog() + "')");
            showToast("Partida guardada en BD :D");
        }catch (Exception e){
            showToast("Error al guardar partida :(");
        }
        db.close();
    }

    private String getFecha(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(d);
    }

    public void goNuevaPartida(View v){
        Intent in = new Intent(Resultados.this,Configuracion.class);
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
            startActivity(Intent.createChooser(i, "Seleccionar aplicación."));
        }else showToast("Necesario un email");
    }

    public void goSalirResultados(View v){
        finish();
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
