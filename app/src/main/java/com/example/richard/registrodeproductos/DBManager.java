package com.example.richard.registrodeproductos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Richard on 10/10/2015.
 */
public class DBManager {
    public static final String TABLE_NAME = "productos";
    public static final String ID = "_id";
    public static final String MARCA = "marca";
    public static final String NOMBRE = "nombre";
    public static final String TIPO = "tipo";
    public static final String EMPRESA = "empresa";
    public static final String CREATE_TABLE = "create table " +
            TABLE_NAME
            + " (" + ID + " integer primary key autoincrement,"
            + MARCA + " text not null,"
            + NOMBRE + " text not null,"
            + TIPO + " text not null,"
            + EMPRESA + " text not null"
            + ");";
    public static final String DROP_TABLE = "drop table id exist " + TABLE_NAME + ";";
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String marca, String nombre, String tipo, String empresa) {
        ContentValues valores = new ContentValues();
        valores.put(MARCA, marca);
        valores.put(NOMBRE, nombre);
        valores.put(TIPO, tipo);
        valores.put(EMPRESA, empresa);
        return valores;
    }

    public boolean insertar(String marca, String nombre, String tipo, String empresa) {
        db.insert(TABLE_NAME, null, generarContentValues(marca, nombre, tipo, empresa));
        return true;
    }

    public void eliminar(int id) {
        db.delete(TABLE_NAME, ID + "=?", new String[]{Integer.toString(id)});
    }

    public boolean actualizar(int id, String marca, String nombre, String tipo, String empresa) {
        db.update(TABLE_NAME, generarContentValues(marca, nombre, tipo, empresa), ID + "=?", new String[]{Integer.toString(id)});
        return true;
    }

    public ArrayList all() {
        ArrayList all = new ArrayList();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            all.add(res.getString(res.getColumnIndex(NOMBRE)));
            res.moveToNext();
        }

        return all;
    }

    public Cursor all(boolean b){
        String[] columnas = new String[]{ID, MARCA, NOMBRE,TIPO, EMPRESA};
        return db.query(TABLE_NAME,columnas, null, null, null, null, null);
    }

    public Cursor getData(Integer id) {
        String[] columnas = new String[]{ID, MARCA, NOMBRE,TIPO, EMPRESA};
        return db.query(TABLE_NAME, columnas, ID + "=?", new String[]{ Integer.toString(id) }, null, null, null);
    }
}
