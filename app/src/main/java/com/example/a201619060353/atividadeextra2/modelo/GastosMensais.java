package com.example.a201619060353.atividadeextra2.modelo;

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

import com.example.a201619060353.atividadeextra2.CadastroGastos;
import com.example.a201619060353.atividadeextra2.ListAdapterGastos;
import com.example.a201619060353.atividadeextra2.R;
import com.example.a201619060353.atividadeextra2.dados.Gasto;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class GastosMensais extends AppCompatActivity {
    private ListView listGastos;
    private GastosDAO bdGastos;
    private Gasto gastoSelecionado;
    private FuncionarioDAO bdFunc;
    private CheckBox gastoPago;
    private FloatingActionMenu botaoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_mensais);
        botaoPrincipal = findViewById(R.id.floatingMenuGastos);
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
        double totalGastos = 0;
        for (Gasto g : arrayGastos)
            totalGastos += g.getValor();
        arrayGastos.add(new Gasto("Total", "", -1, totalGastos));
        ListAdapterGastos adapter = new ListAdapterGastos(this, R.layout.rowlayoutgastos, R.id.txtTipo, arrayGastos);
        listGastos.setAdapter(adapter);
        listGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent = new Intent(GastosMensais.this, CadastroGastos.class);
                if (pos == 0 || pos == arrayGastos.size()) return;
                gastoSelecionado = arrayGastos.get(pos-1);
                Bundle dadosFunc = new Bundle();
                dadosFunc.putSerializable("chave_gasto", gastoSelecionado);
                intent.putExtra("funcao", "atualizar");
                intent.putExtras(dadosFunc);
                startActivity(intent);
            }
        });
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
                    Alert.print(getApplicationContext(), "Despesa não pode ser excluída. Ainda existem funcionários cadastrados!");
                    return false;
                }
                retornoDB = bdGastos.excluirDoBanco(gastoSelecionado);
                carregarLista();
                if(retornoDB != -1){
                    Alert.print(getApplicationContext(), "Despesa excluída com sucesso!");
                    return true;
                }
                Alert.print(getApplicationContext(), "Erro ao excluir despesa, tente novamente!");
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
        botaoPrincipal.close(false);
    }

    public void onClickCadastrarGasto(View view) {
        startActivity(new Intent(this, CadastroGastos.class));
    }
}
