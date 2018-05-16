package com.example.a201619060353.atividadeextra2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class FuncionarioDBHelper extends AbstractDB<Funcionario> {
    private static final String TABLE_FUNC = "funcionario";
    private static final String COLUM_FUNC_ID = "func_id";
    private static final String COLUM_FUNC_NOME = "func_nome";
    private static final String COLUM_FUNC_CARGO = "func_cargo";
    private static final String COLUM_FUNC_SALARIO = "func_salario";
    private BDHelper bdHelper;
    private SQLiteDatabase db;
    private Context context;

    public FuncionarioDBHelper(Context context){
        bdHelper = BDHelper.getBDInstance(context);
        this.context = context;
    }

    @Override
    public long inserirNoBanco(Funcionario f) {
        db = bdHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_FUNC_NOME, f.getNome());
        values.put(COLUM_FUNC_SALARIO, f.getSalario());
        values.put(COLUM_FUNC_CARGO, f.getCargo().getId());
        long retornoDB = db.insert(TABLE_FUNC, null, values);
        db.close();

        return retornoDB;
    }

    @Override
    public long alterarNoBanco(Funcionario f) {
        db = bdHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_FUNC_NOME, f.getNome());
        values.put(COLUM_FUNC_SALARIO, f.getSalario());
        values.put(COLUM_FUNC_CARGO, f.getCargo().getId());
        long retorno = db.update(TABLE_FUNC, values,COLUM_FUNC_ID + " = "+f.getId(), null);
        db.close();
        return retorno;
    }

    @Override
    public long excluirDoBanco(Funcionario f) {
        db = bdHelper.getReadableDatabase();
        long retorno = db.delete(TABLE_FUNC, COLUM_FUNC_ID + " = "+f.getId(), null);
        db.close();
        return retorno;
    }

    @Override
    public ArrayList<Funcionario> selectAll() {
        CargoDBHelper cargoDBHelper = new CargoDBHelper(context);
        db = bdHelper.getReadableDatabase();
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
            cargoNome = cargoDBHelper.buscarCargo(cargoID);
            cargo = new Cargo(cargoID, cargoNome);
            f = new Funcionario(id, nome, cargo, salario);
            arrayFuncionarios.add(f);
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                nome = cursor.getString(1);
                cargoID = cursor.getInt(2);
                salario = cursor.getDouble(3);
                cargoNome = cargoDBHelper.buscarCargo(cargoID);
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
