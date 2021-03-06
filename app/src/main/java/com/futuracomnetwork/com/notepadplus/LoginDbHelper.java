package com.futuracomnetwork.com.notepadplus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Created by gperezc on 31/05/16.
 */

public class LoginDbHelper extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "LoginDb" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    public LoginDbHelper(Context context)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        Log.i(this.getClass().toString(), "Creando base de datos Login");

        db.execSQL( "CREATE TABLE LOGIN(" +
                " _id INTEGER PRIMARY KEY," +
                " usuario TEXT NOT NULL, " +
                " password TEXT NOT NULL, " +
                " pista TEXT)" );

        db.execSQL( "CREATE UNIQUE INDEX usuario ON LOGIN(usuario ASC)" );

        Log.i(this.getClass().toString(), "Tabla LOGIN creada");

   /*
    * Insertamos datos iniciales
    */
        db.execSQL("INSERT INTO LOGIN(_id, usuario, password, pista) VALUES(1,'usuario@notepad.com','1234','usuario@notepad.com 1234')");

        Log.i(this.getClass().toString(), "Datos iniciales LOGIN insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");

        //Aplicamos la sucesivas actualizaciones
        //upgrade_2(db);
        //upgrade_3(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Actualización a versión 2
        if (oldVersion < 2)
        {
            upgrade_2(db);
        }
        // Actualización a versión 3
        if (oldVersion < 3)
        {
            upgrade_3(db);
        }
    }

    private void upgrade_2(SQLiteDatabase db)
    {
        //
        // Upgrade versión 2: definir algunos datos de ejemplo
        //
        db.execSQL( "UPDATE NOTEPAD SET nombre = 'Bienvenido a Notepad'," +
                "             notes = 'Le damos la Bienvenidad a esta nueva aplicacion donde podra crear sus notas mas importantes y tenerlas siempre a la mano, esperamos que esta sea de su agrado'," +
                "             fecha_create = '26/05/2016'" +
                " WHERE _id = 1");

        Log.i(this.getClass().toString(), "Actualización versión 2 finalizada");
    }

    private void upgrade_3(SQLiteDatabase db)
    {
        //
        // Upgrade versión 3: Incluir pasivo_sn
        //
        db.execSQL("ALTER TABLE NOTEPAD ADD importante   VARCHAR2(1) NOT NULL DEFAULT 'N'");

        Log.i(this.getClass().toString(), "Actualización versión 3 finalizada");
    }

}
