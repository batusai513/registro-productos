package com.example.richard.registrodeproductos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContactActivity extends Activity {

    private DBManager manager;
    TextView textNombre, textMarca, textTipo, textEmpresa, textInventario;
    String nombre, marca, tipo, empresa, inventario;
    Button botonGuardar;
    Bundle extras;
    int idToUpdate, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        manager = new DBManager(this);

        textNombre = (TextView) findViewById(R.id.editNombre);
        textMarca = (TextView) findViewById((R.id.editMarca));
        textTipo = (TextView) findViewById(R.id.editTipo);
        textEmpresa = (TextView) findViewById(R.id.editEmpresa);
        textInventario = (TextView) findViewById(R.id.editInventario);

        botonGuardar = (Button) findViewById(R.id.buttonGuardar);

        extras = getIntent().getExtras();

        if(hasExtras()){
            id = idToUpdate = extras.getInt("id");
            System.out.println(id);
            if(id > 0){
                Cursor res = manager.getData(id);
                if(res != null && res.moveToFirst()){
                    getColumnData(res);
                }

                if(!res.isClosed()){
                    res.close();
                }

                fillFields();
                toggleFieldsInteraction(false);
                this.setButtonVisibility(View.INVISIBLE);
            }
        }


    }

    private void getColumnData(Cursor res){
        inventario = res.getString(res.getColumnIndex(DBManager.ID));
        nombre = res.getString(res.getColumnIndex(DBManager.NOMBRE));
        marca = res.getString(res.getColumnIndex(DBManager.MARCA));
        tipo = res.getString(res.getColumnIndex(DBManager.TIPO));
        empresa = res.getString(res.getColumnIndex(DBManager.EMPRESA));
    }


    private String getFieldText(TextView view){
        return view.getText().toString();
    }

    private void fillFields(){
        textInventario.setText(inventario);
        textNombre.setText(nombre);
        textMarca.setText(marca);
        textTipo.setText(tipo);
        textEmpresa.setText(empresa);
    }

    private void toggleFieldsInteraction(Boolean state){
        fieldState(textNombre, state);
        fieldState(textMarca, state);
        fieldState(textTipo, state);
        fieldState(textEmpresa, state);
    }

    private void fieldState(View view, Boolean state){
        view.setEnabled(state);
        view.setFocusableInTouchMode(state);
        view.setClickable(state);
    }

    private void setButtonVisibility(int visibility){
        botonGuardar.setVisibility(visibility);
    }

    public void onClickHandler(View view){
        run(view);
    }

    private boolean hasExtras(){
        return extras != null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(hasExtras()){
            if(id > 0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }else{
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.actions_editar:
                this.setButtonVisibility(View.VISIBLE);
                toggleFieldsInteraction(true);
                return true;
            case R.id.actions_eliminar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialogEliminarProducto).setPositiveButton(R.string.dialogPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        manager.eliminar(idToUpdate);
                        Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.dialogNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view){
        Toast.makeText(getApplicationContext(), "Run: ", Toast.LENGTH_SHORT).show();
        if(hasExtras()){
            if(id > 0){
                if(textNombre.length() > 0 ){
                    if(manager.actualizar(idToUpdate, getFieldText(textMarca), getFieldText(textNombre), getFieldText(textTipo), getFieldText(textEmpresa))){
                        Toast.makeText(getApplicationContext(), "Cargado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "No Cargado", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Agregar un nombre", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (textNombre.length() > 0) {
                    if (manager.insertar(getFieldText(textMarca), getFieldText(textNombre), getFieldText(textTipo), getFieldText(textEmpresa))) {
                        Toast.makeText(getApplicationContext(), "Cargado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Cargado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Agregar un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
