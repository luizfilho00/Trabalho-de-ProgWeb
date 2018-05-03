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

public class BDFuncionarioHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "empresa.db";
    private static final String TABLE_NAME = "funcionario";
    private static final String COLUM_ID = "id";
    private static final String COLUM_NOME = "nome";
    private static final String COLUM_CARGO = "cargo";
    private static final String COLUM_SALARIO = "salario";

    SQLiteDatabase db;

    public static final String TABLE_CREATE = "create table funcionario " +
            "(id integer primary key, nome text not null, salario real not null, cargo integer," +
            "foreign key(id) references cargo(id);";

    public BDFuncionarioHelper(Context context){
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

    public long inserirFuncionario(Funcionario f){
        //Menino ney traz o hexa
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, f.getNome());
        values.put(COLUM_SALARIO, f.getSalario());
        values.put(COLUM_CARGO, f.getCargo());

        long retornoDB = db.insert(TABLE_NAME, null, values);
        db.close();

        return retornoDB;
    }

    public ArrayList<Funcionario> selectAllFuncionarios(){
        db = this.getReadableDatabase();
        String query = "select id, nome, salario from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int id;
        String nome;
        double salario;
        ArrayList<Funcionario> arrayFuncionarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                nome = cursor.getString(1);
                salario = cursor.getDouble(2);
                Funcionario f = new Funcionario(id, nome, salario);
                arrayFuncionarios.add(f);
            }
        }
        return arrayFuncionarios;
    }

    public long alterarFuncionario(Funcionario f){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, f.getNome());
        values.put(COLUM_SALARIO, f.getSalario());
        long retorno = db.update(TABLE_NAME, values,"id = "+f.getId(), null);
        db.close();
        return retorno;
    }

    public long excluirFuncionario(Funcionario f){
        db = this.getReadableDatabase();
        long retorno = db.delete(TABLE_NAME, "id = "+f.getId(), null);
        db.close();
        return retorno;
    }
}
