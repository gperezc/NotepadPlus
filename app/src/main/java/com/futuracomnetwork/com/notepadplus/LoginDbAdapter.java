package com.futuracomnetwork.com.notepadplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by gperezc on 31/05/16.
 */

public class LoginDbAdapter {

    /**
     * Definimos constante con el nombre de la tabla
     */
    public static final String C_TABLA = "LOGIN" ;

    /**
     * Definimos constantes con el nombre de las columnas de la tabla
     */
    public static final String C_COLUMNA_ID   = "_id";
    public static final String C_COLUMNA_USUARIO = "usuario";
    public static final String C_COLUMNA_PASSWORD = "password";
    public static final String C_COLUMNA_PISTA = "pista";


    private Context contexto;
    private LoginDbHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
     */
    private String[] columnas = new String[]{
            C_COLUMNA_ID,
            C_COLUMNA_USUARIO,
            C_COLUMNA_PASSWORD,
            C_COLUMNA_PISTA,} ;

    public LoginDbAdapter(Context context)
    {
        this.contexto = context;
    }

    public LoginDbAdapter abrir() throws SQLException
    {
        dbHelper = new LoginDbHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbHelper.close();
    }

    /**
     * Devuelve cursor con todos las columnas de la tabla*/

     public Cursor getCursor() throws SQLException
     {
     Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);

     return c;
     }


    /**
     * Devuelve cursor con todos las columnas del registro
     */
    public Cursor getRegistro(long id) throws SQLException
    {

        if (db == null)
            abrir();

        Cursor c = db.query( true, C_TABLA, columnas, C_COLUMNA_ID + "=" + id, null, null, null, null, null);

        //Nos movemos al primer registro de la consulta
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Inserta los valores en un registro de la tabla
     * */

    public long insert(ContentValues reg)
    {
        if (db == null)
            abrir();

        return db.insert(C_TABLA, null, reg);
    }

    /**
     * Eliminar el registro con el identificador indicado

    public long delete(long id)
    {
        if (db == null)
            abrir();

        return db.delete(C_TABLA, "_id=" + id, null);
    }*/

    /**
     * Modificar el registro
     */
    public long update(ContentValues reg)
    {
        long result = 0;

        if (db == null)
            abrir();

        if (reg.containsKey(C_COLUMNA_ID))
        {
            //
            // Obtenemos el id y lo borramos de los valores
            //
            long id = reg.getAsLong(C_COLUMNA_ID);

            reg.remove(C_COLUMNA_ID);

            //
            // Actualizamos el registro con el identificador que hemos extraido
            //
            result = db.update(C_TABLA, reg, "_id=" + id, null);
        }
        return result;
    }

    public boolean exists(long id) throws SQLException
    {
        boolean exists ;

        if (db == null)
            abrir();

        Cursor c = db.query( true, C_TABLA, columnas, C_COLUMNA_ID + "=" + id, null, null, null, null, null);

        exists = (c.getCount() > 0);

        c.close();

        return exists;
    }

}
