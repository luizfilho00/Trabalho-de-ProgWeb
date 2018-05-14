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

public class BDHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "empresa.db";
    private static final String TABLE_CARGO = "cargo";
    private static final String COLUM_CARGO_ID = "cargo_id";
    private static final String COLUM_NOME_CARGO = "cargo_nome";
    private static final String TABLE_FUNC = "funcionario";
    private static final String COLUM_FUNC_ID = "func_id";
    private static final String COLUM_FUNC_NOME = "func_nome";
    private static final String COLUM_FUNC_CARGO = "func_cargo";
    private static final String COLUM_FUNC_SALARIO = "func_salario";
    private static final String TABLE_CREATE_FUNC =
            "CREATE TABLE IF NOT EXISTS funcionario (" +
                    "func_id integer primary key, " +
                    "func_nome text not null, " +
                    "func_cargo integer not null," +
                    "func_salario real not null, " +
                    "foreign key(func_cargo) references cargo(cargo_id));";
    private static final String TABLE_CREATE_CARGO =
            "CREATE TABLE IF NOT EXISTS cargo (" +
                    "cargo_id integer primary key, " +
                    "cargo_nome text unique not null);";

    SQLiteDatabase db;

    public BDHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_CARGO);
        sqLiteDatabase.execSQL(TABLE_CREATE_FUNC);
        db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARGO);
        onCreate(db);
    }

    public long inserirNoBanco(Cargo c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME_CARGO, c.getNomeDoCargo());

        long retornoDB = db.insert(TABLE_CARGO, null, values);
        db.close();

        return retornoDB;
    }

    public long alterarNoBanco(Cargo c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME_CARGO, c.getNomeDoCargo());
        long retorno = db.update(TABLE_CARGO, values,COLUM_CARGO_ID + " = "+c.getId(), null);
        db.close();
        return retorno;
    }

    public long excluirDoBanco(Cargo c) {
        db = this.getWritableDatabase();
        long retorno = db.delete(TABLE_CARGO, COLUM_CARGO_ID + " = " + c.getId(), null);
        db.close();
        return retorno;
    }

    public ArrayList<Cargo> selectAllCargo() {
        db = this.getReadableDatabase();
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
        db = this.getReadableDatabase();
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
        db = this.getReadableDatabase();
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

    /******************************* MÉTODOS FUNCIONARIO ****************************/

    public long inserirNoBanco(Funcionario f) {
        //Menino ney traz o hexa

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_FUNC_NOME, f.getNome());
        values.put(COLUM_FUNC_SALARIO, f.getSalario());
        values.put(COLUM_FUNC_CARGO, f.getCargo().getId());
        long retornoDB = db.insert(TABLE_FUNC, null, values);
        db.close();

        return retornoDB;
    }

    public long alterarNoBanco(Funcionario f) {
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_FUNC_NOME, f.getNome());
        values.put(COLUM_FUNC_SALARIO, f.getSalario());
        values.put(COLUM_FUNC_CARGO, f.getCargo().getId());
        long retorno = db.update(TABLE_FUNC, values,COLUM_FUNC_ID + " = "+f.getId(), null);
        db.close();
        return retorno;
    }

    public long excluirDoBanco(Funcionario f) {
        db = this.getReadableDatabase();
        long retorno = db.delete(TABLE_FUNC, COLUM_FUNC_ID + " = "+f.getId(), null);
        db.close();
        return retorno;
    }

    public ArrayList<Funcionario> selectAllFunc() {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FUNC;
        Cursor cursor = db.rawQuery(query, null);
        int id;
        String nome;
        int cargoID;
        double salario;
        String cargoNome;
        Cargo cargo;
        Funcionario f;
        ArrayList<Funcionario> arrayFuncionarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
            nome = cursor.getString(1);
            cargoID = cursor.getInt(2);
            salario = cursor.getDouble(3);
            cargoNome = buscarCargo(cargoID);
            cargo = new Cargo(cargoID, cargoNome);
            f = new Funcionario(id, nome, cargo, salario);
            arrayFuncionarios.add(f);
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                nome = cursor.getString(1);
                cargoID = cursor.getInt(2);
                salario = cursor.getDouble(3);
                cargoNome = buscarCargo(cargoID);
                cargo = new Cargo(cargoID, cargoNome);
                f = new Funcionario(id, nome, cargo, salario);
                arrayFuncionarios.add(f);
            }
        }
        cursor.close();
        db.close();
        return arrayFuncionarios;
    }
}
