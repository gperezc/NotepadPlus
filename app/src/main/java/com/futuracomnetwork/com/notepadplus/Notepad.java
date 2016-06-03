package com.futuracomnetwork.com.notepadplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/*
 * Created by gperezc on 26/05/16.
 */

public class Notepad {

    private Context context;

    private Long id;
    private String nombre;
    private String notes;
    private String fecha_create;
    private boolean importante;


    public Notepad(Context context)
    {
        this.context = context;
    }

    public Notepad(Context context, Long id, String nombre, String notes, String fecha_create, boolean importante) {
        this.context = context;
        this.id = id;
        this.nombre = nombre;
        this.notes = notes;
        this.fecha_create = fecha_create;
        this.importante = importante;
        }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFecha_create() {
        return fecha_create;
    }

    public void setFecha_create(String fecha_create) {
        this.fecha_create = fecha_create;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public static Notepad find(Context context, long id)
    {
        NoteDbAdapter dbAdapter = new NoteDbAdapter(context);

        Cursor c = dbAdapter.getRegistro(id);

        Notepad notepad = Notepad.cursorToNotepad(context, c);

        c.close();

        return notepad;
    }

    public static Notepad cursorToNotepad(Context context, Cursor c)
    {
        Notepad notepad = null;

        if (c != null)
        {
            notepad = new Notepad(context);

            notepad.setId(c.getLong(c.getColumnIndex(NoteDbAdapter.C_COLUMNA_ID)));
            notepad.setNombre(c.getString(c.getColumnIndex(NoteDbAdapter.C_COLUMNA_NOMBRE)));
            notepad.setNotes(c.getString(c.getColumnIndex(NoteDbAdapter.C_COLUMNA_NOTES)));
            notepad.setFecha_create(c.getString(c.getColumnIndex(NoteDbAdapter.C_COLUMNA_FECHA)));
            notepad.setImportante(c.getString(c.getColumnIndex(NoteDbAdapter.C_COLUMNA_IMPORT)).equals("S"));
        }

        return notepad ;
    }

    private ContentValues toContentValues()
    {
        ContentValues reg = new ContentValues();

        reg.put(NoteDbAdapter.C_COLUMNA_ID, this.getId());
        reg.put(NoteDbAdapter.C_COLUMNA_NOMBRE, this.getNombre());
        reg.put(NoteDbAdapter.C_COLUMNA_NOTES, this.getNotes());
        reg.put(NoteDbAdapter.C_COLUMNA_FECHA, this.getFecha_create());
        reg.put(NoteDbAdapter.C_COLUMNA_IMPORT, (this.isImportante())?"S":"N");


        return reg;
    }

    public long save()
    {
        NoteDbAdapter dbAdapter = new NoteDbAdapter(this.getContext());

        // comprobamos si estamos insertando o actualizando según esté o no relleno el identificador
        if ((this.getId() == null) || (!dbAdapter.exists(this.getId())))
        {
            long nuevoId = dbAdapter.insert(this.toContentValues());

            if (nuevoId != -1)
            {
                this.setId(nuevoId);
            }
        }
        else
        {
            dbAdapter.update(this.toContentValues());
        }

        return this.getId();
    }

    public long delete()
    {
        // borramos el registro
        NoteDbAdapter dbAdapter = new NoteDbAdapter(this.getContext());

        return dbAdapter.delete(this.getId());
    }

}
