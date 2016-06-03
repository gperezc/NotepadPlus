package com.futuracomnetwork.com.notepadplus;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

public class NotepadActivity extends ListActivity implements View.OnClickListener{

    //codigo FAB
    private FABToolbarLayout morph;

    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;
    public static final int C_ELIMINAR = 554 ;
    public static final int C_CONFIGURAR = 555 ;
    public static final int C_ABOUT = 556;

    private NoteDbAdapter dbAdapter;
    private Cursor cursor;
    private NoteCursorAdapter noteAdapter ;
    private ListView lista;
    private String filtro ;
    private NotepadAdapter notepadAdapter ;
    private String resultskey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getPreferencias();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //codigo FAB
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        View uno, dos, tres;

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);

        fab.setOnClickListener(this);

        uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                ///        .setAction("Action", null).show();

                Intent i = new Intent(NotepadActivity.this, Edit_Note.class);
                i.putExtra(C_MODO, C_CREAR);
                startActivityForResult(i, C_CREAR);

                morph.hide();
            }
        });

        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotepadActivity.this, Configuracion.class);
                startActivityForResult(i, C_CONFIGURAR);

                morph.hide();
            }
        });

        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotepadActivity.this, About.class);
                startActivityForResult(i, C_ABOUT);

                morph.hide();
            }
        });

        lista = (ListView) findViewById(android.R.id.list);

        NoteDbHelper dbHelper = new NoteDbHelper(getBaseContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Toast.makeText(getBaseContext(), "Base de datos preparada", Toast.LENGTH_SHORT).show();

        dbAdapter = new NoteDbAdapter(this);
        dbAdapter.abrir();

        consultar();

        registerForContextMenu(this.getListView());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            morph.show();
        }
        morph.hide();
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
                    consultar();

            case C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    consultar();

            case C_CONFIGURAR:
                // en la PreferenceActivity no hemos definido ningún resultado por lo que recargamos
                // siempre las preferencias
                getPreferencias();
                consultar();

            case C_ABOUT:
                if (resultCode == RESULT_OK)
                    consultar();

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void consultar()
    {
        cursor = dbAdapter.getCursor(filtro);
        startManagingCursor(cursor);
        noteAdapter = new NoteCursorAdapter(this, cursor);
        lista.setAdapter(noteAdapter);

        /*notepadAdapter = new NotepadAdapter(this, dbAdapter.getNotepads(filtro));

        lista.setAdapter(notepadAdapter);*/
    }

    private void visualizar(long id)
    {
        // Llamamos a la Actividad HipotecaFormulario indicando el modo visualización y el identificador del registro
        Intent i = new Intent(NotepadActivity.this, Edit_Note.class);
        i.putExtra(C_MODO, C_VISUALIZAR);
        i.putExtra(NoteDbAdapter.C_COLUMNA_ID, id);

        startActivityForResult(i, C_VISUALIZAR);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        visualizar(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        Intent i;

        switch (item.getItemId())
        {
            case R.id.menu_crear:
                i = new Intent(this, Edit_Note.class);
                i.putExtra(C_MODO, C_CREAR);
                startActivityForResult(i, C_CREAR);
                return true;

            case R.id.menu_preferencias:
                i = new Intent(NotepadActivity.this, Configuracion.class);
                startActivityForResult(i, C_CONFIGURAR);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        /*
        menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(NoteDbAdapter.C_COLUMNA_NOMBRE)));
        menu.add(Menu.NONE, C_VISUALIZAR, Menu.NONE, R.string.menu_view);
        menu.add(Menu.NONE, C_EDITAR, Menu.NONE, R.string.menu_edit);
        menu.add(Menu.NONE, C_ELIMINAR, Menu.NONE, R.string.menu_delete);*/

        menu.setHeaderTitle(notepadAdapter.getItem(((AdapterView.AdapterContextMenuInfo) menuInfo).position).getNombre());
        menu.add(Menu.NONE, C_VISUALIZAR, Menu.NONE, R.string.menu_view);
        menu.add(Menu.NONE, C_EDITAR, Menu.NONE, R.string.menu_edit);
        menu.add(Menu.NONE, C_ELIMINAR, Menu.NONE, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i;

        switch(item.getItemId())
        {
            case C_ELIMINAR:
                borrar(info.id);
                return true;

            case C_VISUALIZAR:
                visualizar(info.id);
                return true;

            case C_EDITAR:
                i = new Intent(NotepadActivity.this, Edit_Note.class);
                i.putExtra(C_MODO, C_EDITAR);
                i.putExtra(NoteDbAdapter.C_COLUMNA_ID, info.id);

                startActivityForResult(i, C_EDITAR);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void borrar(final long id)
    {
        //
        // Borramos el registro y refrescamos la lista
        //
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.notepad_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.notepad_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                dbAdapter.delete(id);
                Toast.makeText(NotepadActivity.this, R.string.notepad_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
                consultar();
            }
        });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }

    private void getPreferencias()
    {
        //
        // Recuperamos las preferencias
        //
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferencias.getBoolean("ocultar_registros_Importantes", false))
        {
            // si se ocultan registros importantes filtramos solamente los que tengan el valor 'N'
            this.filtro = NoteDbAdapter.C_COLUMNA_IMPORT + " = 'N' " ;
        }
        else
        {
            // si no se ocultan registros importantes no filtramos
            this.filtro = null ;
        }


        /*
        resultskey = preferencias.getString("key_usuario", "");
        Toast toast2 = Toast.makeText(NotepadActivity.this, "este es el texto de la preferencia: " + resultskey, Toast.LENGTH_LONG);
        toast2.show();*/
    }

}
