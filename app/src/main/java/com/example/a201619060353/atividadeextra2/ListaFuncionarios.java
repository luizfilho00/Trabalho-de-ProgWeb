package com.example.a201619060353.atividadeextra2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaFuncionarios extends AppCompatActivity {
    private ListView listaFuncionarios;
    //private ArrayAdapter<Funcionario> funcionarioArrayAdapter;
    private BDHelper bdHelperFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionarios);
        bdHelperFunc = new BDHelper(this);
        listaFuncionarios = findViewById(R.id.listFunc);
        //registerForContextMenu(listaFuncionarios);
        carregarLista();
    }

    private void carregarLista(){
        final ArrayList<Funcionario> listFunc = bdHelperFunc.selectAllFunc();
        if (listFunc == null) return;
        final ArrayAdapter<Funcionario> funcionarioArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listFunc);
        listaFuncionarios.setAdapter(funcionarioArrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarLista();
    }
}
