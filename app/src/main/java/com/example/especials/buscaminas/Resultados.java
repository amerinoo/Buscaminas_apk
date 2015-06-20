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

    
    private Partida p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Tablero tablero = Tablero.getTablero();
        p = new Partida(tablero.partida);
        tablero.clearTablero();
        p.fecha = getFecha();
        Bundle b = getIntent().getExtras();
        boolean smartphone = b.getBoolean("smartphone");
        int bo = b.getInt("banderasOK");
        ((TextView) findViewById(R.id.diaYHora)).setText(p.fecha);
        String log;
        if(smartphone) // Smartphone
            log = "Resultados partida:"
                    + "\n\tAlias: " + p.alias
                    + "\n\tCasillas: " + String.valueOf(p.numeroCasillas) + " Abiertas: " + (p.numeroCasillasRestantes)
                    + "\n\t% Minas: " + String.valueOf(p.porCientoMinas) + "% Banderas OK: " + bo
                    + "\n\tTiempo: " + p.tiempoToString()
                    + "\n\tVictoria? " + p.resultado;
        else log = p.getLog();
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
            i.putExtra(Intent.EXTRA_SUBJECT, "Log - " + p.fecha);
            i.putExtra(Intent.EXTRA_TEXT, p.getLog());
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
