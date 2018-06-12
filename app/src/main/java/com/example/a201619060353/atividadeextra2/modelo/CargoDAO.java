package com.example.a201619060353.atividadeextra2.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a201619060353.atividadeextra2.dados.Cargo;

import java.util.ArrayList;

public class CargoDAO extends AbstractDAO<Cargo> {
    private static final String TABLE_CARGO = "cargo";
    private static final String COLUM_CARGO_ID = "cargo_id";
    private static final String COLUM_NOME_CARGO = "cargo_nome";
    private BDHelper bdHelper;
    private SQLiteDatabase db;
    private Context context;

    public CargoDAO(Context context){
        bdHelper = BDHelper.getBDInstance(context);
        this.context = context;
    }

    @Override
    public long inserirNoBanco(Cargo c) {
        db = bdHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME_CARGO, c.getNomeDoCargo());

        long retornoDB = db.insert(TABLE_CARGO, null, values);
        db.close();

        return retornoDB;
    }

    @Override
    public long alterarNoBanco(Cargo c) {
        db = bdHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME_CARGO, c.getNomeDoCargo());
        long retorno = db.update(TABLE_CARGO, values,COLUM_CARGO_ID + " = "+c.getId(), null);
        db.close();
        return retorno;
    }

    @Override
    public long excluirDoBanco(Cargo c) {
        db = bdHelper.getWritableDatabase();
        long retorno = db.delete(TABLE_CARGO, COLUM_CARGO_ID + " = " + c.getId(), null);
        db.close();
        return retorno;
    }

    @Override
    public ArrayList<Cargo> selectAll() {
        db = bdHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_CARGO;
        Cursor cursor = db.rawQuery(query, null);
        int id;
        String nome;
        Cargo c;
        ArrayList<Cargo> arrayCargos = new ArrayList<>();
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
            nome = cursor.getString(1);
            c = new Cargo(id, nome);
            arrayCargos.add(c);
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                nome = cursor.getString(1);
                c = new Cargo(id, nome);
                arrayCargos.add(c);
            }
        }
        cursor.close();
        db.close();
        return arrayCargos;
    }

    public int buscarCargo(String nomeCargo){
        db = bdHelper.getReadableDatabase();
        String query = "SELECT " + COLUM_CARGO_ID +
                " FROM " + TABLE_CARGO +
                " WHERE " + COLUM_NOME_CARGO + " like '" + nomeCargo + "';";
        Cursor cursor = db.rawQuery(query, null);
        int id = -1;
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    public String buscarCargo(int cargoID){
        db = bdHelper.getReadableDatabase();
        String query = "SELECT " + COLUM_NOME_CARGO +
                " FROM " + TABLE_CARGO +
                " WHERE " + COLUM_CARGO_ID + " = " + cargoID + ";";
        Cursor cursor = db.rawQuery(query, null);
        String nomeCargo = "";
        if (cursor.moveToFirst()){
            nomeCargo = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return nomeCargo;
    }
}
