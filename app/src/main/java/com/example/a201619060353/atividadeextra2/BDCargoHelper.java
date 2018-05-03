package com.example.a201619060353.atividadeextra2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 201619060353 on 03/04/2018.
 */

public class BDCargoHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "empresa.db";
    private static final String TABLE_NAME = "cargo";
    private static final String COLUM_ID = "id";
    private static final String COLUM_NOMECARGO = "nome";

    SQLiteDatabase db;

    public static final String TABLE_CREATE = "create table cargo " +
            "(id integer primary key, nome text not null);";

    public BDCargoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public long inserirCargo(Cargo c){
        //Menino ney traz o hexa
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOMECARGO, c.getNomeDoCargo());

        long retornoDB = db.insert(TABLE_NAME, null, values);
        db.close();

        return retornoDB;
    }

    public ArrayList<Cargo> selectAllCargos(){
        db = this.getReadableDatabase();
        String query = "select id, nome from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int id;
        String nome;
        double salario;
        ArrayList<Cargo> arrayCargos = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                nome = cursor.getString(1);
                salario = cursor.getDouble(2);
                Cargo c = new Cargo(id, nome);
                arrayCargos.add(c);
            }
        }
        return arrayCargos;
    }

    public long alterarCargo(Cargo c){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOMECARGO, c.getNomeDoCargo());
        long retorno = db.update(TABLE_NAME, values,"id = "+c.getId(), null);
        db.close();
        return retorno;
    }

    public long excluirCargo(Cargo c){
        db = this.getReadableDatabase();
        long retorno = db.delete(TABLE_NAME, "id = "+c.getId(), null);
        db.close();
        return retorno;
    }
}
