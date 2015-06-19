package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Resultados extends Activity {

    private String alias;
    private int casillas;
    private int porciento;
    private String log;
    private String date;
    private int tiempo;
    private String resultado;
    private int n;
    private int bo;
    private Partida p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Tablero tablero = Tablero.getTablero();
        p = new Partida(tablero.partida);
        tablero.clearTablero();
        Date fecha = new Date();
        Bundle b = getIntent().getExtras();
        log = b.getString("log");
        System.out.println(log);
        alias = b.getString("alias");
        casillas = b.getInt("casillas");
        porciento = b.getInt("porciento");
        tiempo = b.getInt("tiempo");
        resultado = b.getString("resultado");
        n = b.getInt("casillasRestantes");
        bo = b.getInt("bombasRestantes");
        date = fecha.toLocaleString();
        String t = (tiempo == 0) ? "No usado" : String.valueOf(tiempo) + " segundos";
        if(log == null)
            log = "Resultados partida:"
                    + "\n\tAlias: " + alias
                    + "\n\tCasillas: " + String.valueOf(casillas) + " Abiertas: " + n
                    + "\n\t% Minas: " + String.valueOf(porciento) + "% Banderas OK: " + bo
                    + "\n\tTiempo: " + t
                    + "\n\tVictoria? " + resultado;
        ((TextView) findViewById(R.id.diaYHora)).setText(date);
        ((TextView) findViewById(R.id.log)).setText(log);

        toDB();

    }

    /*

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        //Generamos los datos de muestra
        String alias = new String("Albert");
        String fecha = format.format(d);
        int numeroCasillas = 20;
        int numeroCasillasRestantes = 0;
        int porCientoMinas = 25;
        int tiempo = 1;
        String resultado = new String("Victoria");
        String bomba = null;


        //Insertamos los datos en la tabla Clientes
        db.execSQL("INSERT INTO Partidas (alias, fecha, numeroCasillas, numeroCasillasRestantes,porCientoMinas, tiempo, resultado, bomba) " +
                "VALUES ('" + alias + "', '" + fecha +"', '" + numeroCasillas + "', '" + numeroCasillasRestantes + "', '" + porCientoMinas + "', '" + tiempo + "', '" + resultado + "', '" + bomba + "')");
     */
    private void toDB() {
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBPartidas", null, 2);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Insertamos los datos en la tabla Clientes

        /*db.execSQL("INSERT INTO Partidas (alias, fecha, numeroCasillas, numeroCasillasRestantes,porCientoMinas, tiempo, resultado, bomba) " +
                "VALUES ('" + alias + "', '" + fecha +"', '" + numeroCasillas + "', '" + numeroCasillasRestantes + "', '" + porCientoMinas + "', '" + tiempo + "', '" + resultado + "', '" + bomba + "')");*/

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
            i.putExtra(Intent.EXTRA_SUBJECT, "Log - " + date);
            i.putExtra(Intent.EXTRA_TEXT, log);
            startActivity(Intent.createChooser(i, "Seleccionar aplicación."));
        }else showToast("Necesario un email");
    }

    public void goSalirResultados(View v){
        finish();
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


}
