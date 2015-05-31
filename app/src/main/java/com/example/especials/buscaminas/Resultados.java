package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Tablero.getTablero().clearTablero();
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
