package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class GastosMensais extends AppCompatActivity {
    private ListView listGastos;
    private GastosDAO bdGastos;
    private Gasto gastoSelecionado;
    private FuncionarioDAO bdFunc;
    private CheckBox gastoPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_mensais);
        bdGastos = new GastosDAO(this);
        bdFunc = new FuncionarioDAO(this);
        listGastos = findViewById(R.id.listGastos);
        carregarLista();
        registerForContextMenu(listGastos);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.headergastos, listGastos, false);
        listGastos.addHeaderView(headerView);
    }

    private void carregarLista() {
        final ArrayList<Gasto> arrayGastos = bdGastos.selectAll();
        ListAdapterGastos adapter = new ListAdapterGastos(this, R.layout.rowlayoutgastos, R.id.txtTipo, arrayGastos);
        listGastos.setAdapter(adapter);
        listGastos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) return false;
                gastoSelecionado = arrayGastos.get(pos-1);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add(Menu.NONE, 0, 1, "Deletar");
        MenuItem mSair = menu.add(Menu.NONE, 1, 2, "Cancelar");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoDB;
                if (gastoSelecionado.getTipo().equals("Salários") && bdFunc.getQtdFunc() > 0){
                    alert("Despesa não pode ser excluída. Ainda existem funcionários cadastrados!");
                    return false;
                }
                retornoDB = bdGastos.excluirDoBanco(gastoSelecionado);
                carregarLista();
                if(retornoDB != -1){
                    alert("Despesa excluída com sucesso!");
                    return true;
                }
                alert("Erro ao excluir despesa, tente novamente!");
                return false;
            }
        });

        mSair.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    public void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void onClickCadastrarGasto(View view) {
        startActivity(new Intent(this, CadastroGastos.class));
    }
}
