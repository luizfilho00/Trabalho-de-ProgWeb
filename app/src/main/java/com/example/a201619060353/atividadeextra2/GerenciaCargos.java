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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Comparator;

public class GerenciaCargos extends AppCompatActivity {
    private ListView listCargos;
    private CargoDAO dbHelperCargo;
    private Cargo cargoSelecionado;
    private FloatingActionMenu menuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerencia_cargos);
        menuPrincipal = findViewById(R.id.floatingMenuGC);
        dbHelperCargo = new CargoDAO(this);
        listCargos = findViewById(R.id.listCargos);
        registerForContextMenu(listCargos);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.headercargos, listCargos, false);
        listCargos.addHeaderView(headerView);
        carregarLista();
    }

    private void carregarLista(){
        final ArrayList<Cargo> listCargo = dbHelperCargo.selectAll();
        ListAdapterCargos adapter = new ListAdapterCargos(this, R.layout.rowlayoutcargos, R.id.txtCargoGC, listCargo);
        listCargo.sort(new Comparator<Cargo>() {
            @Override
            public int compare(Cargo cargo, Cargo t1) {
                return cargo.getNomeDoCargo().compareToIgnoreCase(t1.getNomeDoCargo());
            }
        });
        if (listCargo == null) return;
        listCargos.setAdapter(adapter);
        listCargos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(GerenciaCargos.this, CadastroCargo.class);
                if (pos == 0) return;
                cargoSelecionado = listCargo.get(pos-1);
                Bundle dadosCargo = new Bundle();
                dadosCargo.putSerializable("chave_cargo", cargoSelecionado);
                intent.putExtra("funcao", "atualizar");
                intent.putExtras(dadosCargo);
                startActivity(intent);
            }
        });
        listCargos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) return false;
                cargoSelecionado = listCargo.get(pos-1);
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
                retornoDB = dbHelperCargo.excluirDoBanco(cargoSelecionado);
                carregarLista();
                if(retornoDB != -1){
                    alert("Cargo excluído com sucesso!");
                    return true;
                }
                alert("Erro ao excluir cargo, tente novamente!");
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

    public void onClickCadastrarFuncGC(View view) {
        Intent intent = new Intent(this, CadastroFuncionario.class);
        startActivity(intent);
        finish();
    }

    public void onClickCadastrarCargoGC(View view) {
        Intent intent = new Intent(this, CadastroCargo.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarLista();
        menuPrincipal.close(false);
    }

    private void alert(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
