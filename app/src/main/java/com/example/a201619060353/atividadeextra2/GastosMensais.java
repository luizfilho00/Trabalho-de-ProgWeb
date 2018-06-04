package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

public class GastosMensais extends AppCompatActivity {
    private ListView listGastos;
    private FuncionarioDAO bdFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_mensais);

        bdFunc = new FuncionarioDAO(this);
        listGastos = findViewById(R.id.listGastos);
        carregarlista();
        registerForContextMenu(listGastos);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.headergastos, listGastos, false);
        listGastos.addHeaderView(headerView);
    }

    private void carregarlista() {

        final ArrayList<Double> arrayGastos = bdFunc.selectAllSalario();

        ListAdapterGastos adapter = new ListAdapterGastos(this, R.layout.rowlayout, R.id.txtSalario, arrayGastos);
        if (arrayGastos == null) return;
        listGastos.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarlista();
    }
}
