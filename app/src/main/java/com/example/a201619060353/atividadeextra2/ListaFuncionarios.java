package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class ListaFuncionarios extends AppCompatActivity {
    private ListView listaFuncionarios;
    private BDHelper bdHelperFunc;
    private Funcionario funcSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionarios);
        bdHelperFunc = new BDHelper(this);
        listaFuncionarios = findViewById(R.id.listFunc);
        registerForContextMenu(listaFuncionarios);
        carregarLista();
    }

    private void carregarLista(){
        final ArrayList<Funcionario> listFunc = bdHelperFunc.selectAllFunc();
        listFunc.sort(new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario f1, Funcionario f2) {
                return f1.getNome().compareToIgnoreCase(f2.getNome());
            }
        });
        if (listFunc == null) return;
        final ArrayAdapter<Funcionario> funcionarioArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listFunc);
        listaFuncionarios.setAdapter(funcionarioArrayAdapter);
        listaFuncionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(ListaFuncionarios.this, CadastroFuncionario.class);
                funcSelecionado = listFunc.get(pos);
                Bundle dadosFunc = new Bundle();
                dadosFunc.putSerializable("chave_func", funcSelecionado);
                intent.putExtra("funcao", "atualizar");
                intent.putExtras(dadosFunc);
                startActivity(intent);
            }
        });
        listaFuncionarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                funcSelecionado = listFunc.get(pos);
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
                retornoDB = bdHelperFunc.excluirDoBanco(funcSelecionado);
                bdHelperFunc.close();
                carregarLista();
                if(retornoDB != -1){
                    alert("Funcionário excluído com sucesso!");
                    return true;
                }
                alert("Erro ao excluir funcionário, tente novamente!");
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
    protected void onRestart() {
        super.onRestart();
        carregarLista();
    }

    private void alert(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
