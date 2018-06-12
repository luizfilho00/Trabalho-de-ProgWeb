package com.example.a201619060353.atividadeextra2;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a201619060353.atividadeextra2.dados.Funcionario;
import com.example.a201619060353.atividadeextra2.modelo.Alert;
import com.example.a201619060353.atividadeextra2.modelo.FuncionarioDAO;
import com.example.a201619060353.atividadeextra2.modelo.GastosMensais;
import com.example.a201619060353.atividadeextra2.modelo.GerenciaCargos;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ListView listaFuncionarios;
    private FuncionarioDAO dbHelperFunc;
    private Funcionario funcSelecionado;
    private FloatingActionMenu menuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuPrincipal = findViewById(R.id.floatingMenu);
        dbHelperFunc = new FuncionarioDAO(this);
        listaFuncionarios = findViewById(R.id.listFunc);
        registerForContextMenu(listaFuncionarios);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header, listaFuncionarios, false);
        listaFuncionarios.addHeaderView(headerView);
        listaFuncionarios.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        carregarLista();
    }

    public void onClickCadastrarFunc(View view) {
        Intent intent = new Intent(this, CadastroFuncionario.class);
        startActivity(intent);
    }

    private void carregarLista(){
        final ArrayList<Funcionario> listFunc = dbHelperFunc.selectAll();
        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout, R.id.txtNome, listFunc);
        listFunc.sort(new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario f1, Funcionario f2) {
                return f1.getNome().compareToIgnoreCase(f2.getNome());
            }
        });
        if (listFunc == null) return;
        listaFuncionarios.setAdapter(adapter);
        listaFuncionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(MainActivity.this, CadastroFuncionario.class);
                if (pos == 0) return;
                funcSelecionado = listFunc.get(pos-1);
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
                if (pos == 0) return false;
                funcSelecionado = listFunc.get(pos-1);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add(Menu.NONE, 0, 1, "Deletar");
        MenuItem mSair = menu.add(Menu.NONE, 1, 2, "Cancelar");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoDB;
                retornoDB = dbHelperFunc.excluirDoBanco(funcSelecionado);
                carregarLista();
                if (retornoDB != -1) {
                    Alert.print(getApplicationContext(), "Funcionário excluído com sucesso!");
                    return true;
                }
                Alert.print(getApplicationContext(), "Erro ao excluir funcionário, tente novamente!");
                return false;
            }
        });

        mSair.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void onClickGerenciarCargos(View view) {
        Intent intent = new Intent(this, GerenciaCargos.class);
        startActivity(intent);
    }

    public void onClickGastosMensais(View view) {
        Intent intent = new Intent(this, GastosMensais.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
        menuPrincipal.close(false);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja realmente sair?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
