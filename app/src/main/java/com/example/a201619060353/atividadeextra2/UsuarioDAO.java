package com.example.a201619060353.atividadeextra2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UsuarioDAO extends AbstractDAO<Usuario> {
    private static final String TABLE_USUARIO = "usuario";
    private static final String COLUM_USUARIO_ID = "usuario_id";
    private static final String COLUM_USUARIO_LOGIN = "usuario_login";
    private static final String COLUM_USUARIO_SENHA = "usuario_senha";
    private BDHelper bdHelper;
    private SQLiteDatabase db;
    private Context context;

    public UsuarioDAO (Context context){
        bdHelper = BDHelper.getBDInstance(context);
        this.context = context;
    }

    public Usuario getUser(){
        db = bdHelper.getReadableDatabase();
        String query = "SELECT * " + "FROM " + TABLE_USUARIO;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return new Usuario(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2));
        }
        return null;
    }

    @Override
    public long inserirNoBanco(Usuario u) {
        db = bdHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_USUARIO_ID, u.getId());
        values.put(COLUM_USUARIO_LOGIN, u.getLogin());
        values.put(COLUM_USUARIO_SENHA, u.getSenha());
        long retornoDB = db.insert(TABLE_USUARIO, null, values);
        db.close();
        return retornoDB;
    }

    @Override
    public long alterarNoBanco(Usuario objeto) {
        return -1;
    }

    @Override
    public long excluirDoBanco(Usuario objeto) {
        return -1;
    }

    @Override
    public ArrayList<Usuario> selectAll() {
        return null;
    }
}
