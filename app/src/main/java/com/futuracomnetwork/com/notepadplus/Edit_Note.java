package com.futuracomnetwork.com.notepadplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.futuracomnetwork.com.notepadplus.NotepadActivity.C_CREAR;
import static com.futuracomnetwork.com.notepadplus.NotepadActivity.C_VISUALIZAR;

public class Edit_Note extends AppCompatActivity {


    private NoteDbAdapter dbAdapter;
    private Cursor cursor;

    //
    // Modo del formulario
    //
    private int modo ;

    //
    // Identificador del registro que se edita cuando la opción es MODIFICAR
    //
    private long id ;

    //
    // Elementos de la vista
    //
    private EditText nombre;
    private EditText notes;
    private TextView fecha_create;
    private CheckBox importante;

    public String fechaHoraActual(){
        return new SimpleDateFormat( "dd-MM-yyyy_HH:mm:ss", java.util.Locale.getDefault()).format(Calendar.getInstance() .getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__note);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        //
        // Obtenemos los elementos de la vista
        //
        nombre = (EditText) findViewById(R.id.nombre);
        notes = (EditText) findViewById(R.id.notes);
        fecha_create = (TextView) findViewById(R.id.fecha_create);
        importante = (CheckBox) findViewById(R.id.importante);

        //
        // Creamos el adaptador
        //
        dbAdapter = new NoteDbAdapter(this);
        dbAdapter.abrir();

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(NoteDbAdapter.C_COLUMNA_ID))
        {
            id = extra.getLong(NoteDbAdapter.C_COLUMNA_ID);
            consultar(id);
        }

        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(NotepadActivity.C_MODO));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        if (modo == C_VISUALIZAR)
            getMenuInflater().inflate(R.menu.noteedit_ver, menu);
        else
            getMenuInflater().inflate(R.menu.noteedit_editar, menu);
        return true;

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.noteedit_activity_actions, menu);
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar itemsi = new Intent(this, NoteEdit.class);
                //i.putExtra(C_MODO, C_CREAR);
                //startActivityForResult(i, C_CREAR);
        switch (item.getItemId()) {
            case R.id.action_borrar:
                borrar(id);
                return true;
            case R.id.action_save:
                guardar();
                return true;
            case R.id.action_revert:
                cancelar();
                return true;
            case R.id.action_editar:
                invalidateOptionsMenu();
                establecerModo(NotepadActivity.C_EDITAR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_borrar:
                borrar(id);
                return true;

            case R.id.action_revert:
                cancelar();
                return true;

            case R.id.action_save:
                guardar();
                return true;

            case R.id.action_editar:
                establecerModo(NotepadActivity.C_EDITAR);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }*/

    private void establecerModo(int m)
    {
        this.modo = m ;

        if (modo == NotepadActivity.C_VISUALIZAR)
        {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        }
        else if (modo == NotepadActivity.C_CREAR)
        {
            this.setTitle(R.string.notepad_crear_titulo);
            this.setEdicion(true);
        }
        else if (modo == NotepadActivity.C_EDITAR)
        {
            this.setTitle(R.string.notepad_editar_titulo);
            this.setEdicion(true);
        }
    }

    private void consultar(long id)
    {
        //
        // Consultamos el centro por el identificador
        //
        ///cursor = dbAdapter.getRegistro(id);

        Notepad notepad = Notepad.find(this, id);

        ///nombre.setText(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.C_COLUMNA_NOMBRE)));
        ///notes.setText(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.C_COLUMNA_NOTES)));
        ///fecha_create.setText(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.C_COLUMNA_FECHA)));
        ///importante.setChecked(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.C_COLUMNA_IMPORT)).equals("S"));

        nombre.setText(notepad.getNombre());
        notes.setText(notepad.getNotes());
        fecha_create.setText(notepad.getFecha_create());
        importante.setChecked(notepad.isImportante());

    }

    private void setEdicion(boolean opcion)
    {
        nombre.setEnabled(opcion);
        notes.setEnabled(opcion);
        fecha_create.setEnabled(opcion);
        importante.setEnabled(opcion);
    }

    private void guardar()
    {
        //
        // Obtenemos los datos del formulario
        //
        /*ContentValues reg = new ContentValues();

        reg.put(NoteDbAdapter.C_COLUMNA_NOMBRE, nombre.getText().toString());
        reg.put(NoteDbAdapter.C_COLUMNA_NOTES, notes.getText().toString());
        reg.put(NoteDbAdapter.C_COLUMNA_FECHA, fechaHoraActual());


        if (modo == NotepadActivity.C_EDITAR)
            reg.put(NoteDbAdapter.C_COLUMNA_ID, id);

        reg.put(NoteDbAdapter.C_COLUMNA_NOMBRE, nombre.getText().toString());
        reg.put(NoteDbAdapter.C_COLUMNA_NOTES, notes.getText().toString());
        reg.put(NoteDbAdapter.C_COLUMNA_FECHA, fechaHoraActual());
        reg.put(NoteDbAdapter.C_COLUMNA_IMPORT, (importante.isChecked())?"S":"N");


        if (modo == NotepadActivity.C_CREAR)
        {
            dbAdapter.insert(reg);
            Toast.makeText(NoteEdit.this, R.string.notepad_crear_confirmacion, Toast.LENGTH_SHORT).show();
        }
        else if (modo == NotepadActivity.C_EDITAR)
        {
            Toast.makeText(NoteEdit.this, R.string.notepad_editar_confirmacion, Toast.LENGTH_SHORT).show();
            dbAdapter.update(reg); */

        Notepad notepad ;

        if (this.modo == NotepadActivity.C_EDITAR)
        {
            notepad = Notepad.find(this, this.id);
        }
        else
        {
            notepad = new Notepad(this) ;
        }

        notepad.setNombre(nombre.getText().toString());
        notepad.setNotes(notes.getText().toString());
        //notepad.setFecha_create(fecha_create.getText().toString());
        notepad.setFecha_create(fechaHoraActual());
        notepad.setImportante(importante.isChecked());

        notepad.save();

        if (modo == NotepadActivity.C_CREAR)
        {
            Toast.makeText(Edit_Note.this, R.string.notepad_crear_confirmacion, Toast.LENGTH_SHORT).show();
        }
        else if (modo == NotepadActivity.C_EDITAR)
        {
            Toast.makeText(Edit_Note.this, R.string.notepad_editar_confirmacion, Toast.LENGTH_SHORT).show();
        }

        //
        // Devolvemos el control
        //
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar()
    {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void borrar(final long id)
    {
        /**
         * Borramos el registro con confirmación
         */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.notepad_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.notepad_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                dbAdapter.delete(id);
                Toast.makeText(Edit_Note.this, R.string.notepad_eliminar_confirmacion, Toast.LENGTH_SHORT).show();

                /**
                 * Devolvemos el control
                 */
                setResult(RESULT_OK);
                finish();
            }
        });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //
        // Nos aseguramos que es la petición que hemos realizado
        //
        switch(requestCode)
        {
            case C_CREAR:
                if (resultCode == RESULT_OK)
                    consultar(id);
            case C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    consultar(id);

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
