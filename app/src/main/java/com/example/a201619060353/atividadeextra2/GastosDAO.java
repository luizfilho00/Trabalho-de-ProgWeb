package com.example.a201619060353.atividadeextra2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class GastosDAO extends AbstractDAO<Gasto> {
    private static final String TABLE_GASTO = "gasto";
    private static final String COLUM_GASTO_ID = "gasto_id";
    private static final String COLUM_TIPO_GASTO = "gasto_tipo";
    private static final String COLUM_DATA_PAGAMENTO = "gasto_data";
    private static final String COLUM_FLAG_PAGO = "gasto_pago";
    private static final String COLUM_VALOR_GASTO = "gasto_valor";
    private BDHelper bdHelper;
    private SQLiteDatabase db;
    private Context context;

    public GastosDAO(Context context){
        bdHelper = BDHelper.getBDInstance(context);
        this.context = context;
    }

    @Override
    public long inserirNoBanco(Gasto g) {
        db = bdHelper.getWritableDatabase();
        String query = "SELECT *"+
                " FROM " + TABLE_GASTO +
                " WHERE " + COLUM_TIPO_GASTO + " like '" + g.getTipo() + "';";
        Cursor cursor = db.rawQuery(query, null);
        int id = -1;
        String tipoCadastrado = "";
        double valorAtual = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            tipoCadastrado = cursor.getString(1);
            valorAtual = cursor.getDouble(2);
        }
        cursor.close();
        if (tipoCadastrado.equals(g.getTipo())){
            db.close();
            g.setId(id);
            g.setValor(g.getValor() + valorAtual);
            alterarNoBanco(g);
            return 0;
        }
        ContentValues values = new ContentValues();
        values.put(COLUM_TIPO_GASTO, g.getTipo());
        values.put(COLUM_DATA_PAGAMENTO, g.getData());
        values.put(COLUM_FLAG_PAGO, g.getPago());
        values.put(COLUM_VALOR_GASTO, g.getValor());
        long retornoDB = db.insert(TABLE_GASTO, null, values);
        db.close();
        return retornoDB;
    }

    @Override
    public long alterarNoBanco(Gasto g) {
        db = bdHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_TIPO_GASTO, g.getTipo());
        values.put(COLUM_DATA_PAGAMENTO, g.getData());
        values.put(COLUM_FLAG_PAGO, g.getPago());
        values.put(COLUM_VALOR_GASTO, g.getValor());
        long retorno = db.update(TABLE_GASTO, values,COLUM_GASTO_ID + " = " + g.getId(), null);
        db.close();
        return retorno;
    }

    public long decrementarGasto(Gasto g, double decremento){
        db = bdHelper.getWritableDatabase();
        String query = "SELECT " + COLUM_GASTO_ID + "," + COLUM_VALOR_GASTO +
                " FROM " + TABLE_GASTO +
                " WHERE " + COLUM_TIPO_GASTO + " like '" + g.getTipo() + "';";
        Cursor cursor = db.rawQuery(query, null);
        double valorNoBanco = 0, novoValor = 0;
        int id;
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
            valorNoBanco = cursor.getDouble(1);
        }
        else
            return -1;
        if (valorNoBanco - decremento >= 0)
            novoValor = valorNoBanco - decremento;
        ContentValues values = new ContentValues();
        values.put(COLUM_TIPO_GASTO, g.getTipo());
        values.put(COLUM_DATA_PAGAMENTO, g.getData());
        values.put(COLUM_FLAG_PAGO, g.getPago());
        values.put(COLUM_VALOR_GASTO, novoValor);
        long retorno = db.update(TABLE_GASTO, values,COLUM_GASTO_ID + " = " + id, null);
        cursor.close();
        db.close();
        return retorno;
    }

    @Override
    public long excluirDoBanco(Gasto g) {
        db = bdHelper.getWritableDatabase();
        long retorno = db.delete(TABLE_GASTO, COLUM_GASTO_ID + " = " + g.getId(), null);
        db.close();
        return retorno;
    }

    @Override
    public ArrayList<Gasto> selectAll() {
        db = bdHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_GASTO;
        Cursor cursor = db.rawQuery(query, null);
        int id, pago;
        String tipo, data = "";
        double valor;
        Gasto g;
        ArrayList<Gasto> arrayGastos = new ArrayList<>();
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
            tipo = cursor.getString(1);
            data = cursor.getString(2);
            pago = cursor.getInt(3);
            valor = cursor.getDouble(4);
            g = new Gasto(id, tipo, data, pago, valor);
            arrayGastos.add(g);
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                tipo = cursor.getString(1);
                data = cursor.getString(2);
                pago = cursor.getInt(3);
                valor = cursor.getDouble(4);
                g = new Gasto(id, tipo, data, pago, valor);
                arrayGastos.add(g);
            }
        }
        cursor.close();
        db.close();
        return arrayGastos;
    }

    public Gasto selectAllSalario(){
        FuncionarioDAO funcDAO = new FuncionarioDAO(context);
        double gastosSalario = funcDAO.getAllSalario();
        return new Gasto("Salários", Datas.PAGAMENTO_FUNC, 0, gastosSalario);
    }
}
