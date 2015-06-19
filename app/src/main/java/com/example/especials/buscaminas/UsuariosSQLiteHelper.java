package com.example.especials.buscaminas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios

    String sqlCreate = "CREATE TABLE Partidas " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " alias TEXT, " +
            " fecha TEXT, "+
            " numeroCasillas INTEGER, " +
            " numeroCasillasRestantes INTEGER, "+
            " porCientoMinas INTEGER, "+
            " tiempo INTEGER, "+
            " resultado TEXT, "+
            " bomba TEXT)";

    public UsuariosSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);

        //Generamos los datos de muestra
        String alias = new String("Albert");
        String fecha = new String("fecha");
        int numeroCasillas = 20;
        int numeroCasillasRestantes = 0;
        int porCientoMinas = 25;
        int tiempo = 1;
        String resultado = new String("victoria");
        String bomba = null;


        //Insertamos los datos en la tabla Clientes
        db.execSQL("INSERT INTO Partidas (alias, fecha, numeroCasillas, numeroCasillasRestantes,porCientoMinas, tiempo, reultado, bomba) " +
                "VALUES ('" + alias + "', '" + fecha +"', '" + numeroCasillas + "', '" + numeroCasillasRestantes + "', '" + porCientoMinas + "', '" + tiempo + "', '" + resultado + "', '" + bomba + "')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
                          int versionNueva) {

        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Partidas");

        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }

}

