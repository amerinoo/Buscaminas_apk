package com.example.especials.buscaminas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    static String nameTable = "Partidas";
    //Sentencia SQL para crear la tabla de Usuarios

    String sqlCreate = "CREATE TABLE " + nameTable + "  " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "alias TEXT, " +
            "fecha TEXT, "+
            "numeroCasillas INTEGER, " +
            "numeroCasillasRestantes INTEGER, "+
            "porCientoMinas INTEGER, "+
            "tiempo INTEGER, "+
            "resultado TEXT, "+
            "bomba TEXT)";

    public UsuariosSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);
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

