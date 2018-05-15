package com.example.a201619060353.atividadeextra2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class CadastroFuncionario extends AppCompatActivity {
    private int idFunc = -1;
    private EditText nomeFunc;
    private EditText salarioFunc;
    private Spinner spinnerCargo;
    private FuncionarioDBHelper dbHelperFunc;
    private CargoDBHelper dbHelperCargo;
    private String novoCargo = "Novo cargo...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        dbHelperCargo = new CargoDBHelper(this);
        dbHelperFunc = new FuncionarioDBHelper(this);
        nomeFunc = findViewById(R.id.edtNomeFunc);
        salarioFunc = findViewById(R.id.edtSalario);
        spinnerCargo = findViewById(R.id.spinCargo);
        carregarDados();
    }

    private void carregarDados(){
        final ArrayList<Cargo> listCargos = dbHelperCargo.selectAll();
        listCargos.sort(new Comparator<Cargo>() {
            @Override
            public int compare(Cargo cargo, Cargo t1) {
                return cargo.getNomeDoCargo().compareToIgnoreCase(t1.getNomeDoCargo());
            }
        });
        listCargos.add(0, new Cargo(" "));
        listCargos.add(new Cargo(novoCargo));
        ArrayAdapter<Cargo> listCargoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listCargos);
        listCargoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(listCargoAdapter);
        spinnerCargo.setSelection(0);
        spinnerCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == listCargos.size() - 1){
                    Intent intent = new Intent(CadastroFuncionario.this, CadastroCargo.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        if (getIntent().getStringExtra("funcao") != null){
            if(getIntent().getStringExtra("funcao").equals("atualizar")){
                Bundle b = getIntent().getExtras();
                Funcionario f = (Funcionario) b.getSerializable("chave_func");
                idFunc = f.getId();
                nomeFunc.setText(f.getNome());
                Cargo cargo = f.getCargo();
                spinnerCargo.setSelection(listCargoAdapter.getPosition(cargo));
                salarioFunc.setText(String.valueOf(f.getSalario()));
                ((Button) findViewById(R.id.btnVariavel)).setText("Alterar");
            }
        }
    }

    public void salvarFuncionario(View view) {
        if (spinnerCargo == null || spinnerCargo.getSelectedItem().toString().equals(" ")){
            alert("Por favor selecione um Cargo!");
            return;
        }
        if (isNull()){
            alert("Por favor preencha todos os campos!");
            return;
        }
        String nome = nomeFunc.getText().toString();
        double salario = Double.valueOf(salarioFunc.getText().toString());
        String cargoNome = spinnerCargo.getSelectedItem().toString();
        int cargoId = dbHelperCargo.buscarCargo(cargoNome);
        Cargo cargo = new Cargo(cargoId, cargoNome);
        Funcionario f;
        long result;

        if (getIntent().getStringExtra("funcao") != null){
            if (getIntent().getStringExtra("funcao").equals("atualizar")){
                f = new Funcionario(idFunc, nome, cargo, salario);
                result = dbHelperFunc.alterarNoBanco(f);
                if(result != -1){
                    alert("Funcionário alterado com sucesso!");
                }else{
                    alert("Ocorreu um erro, por favor tente novamente.");
                }
            }
        }
        else{
            f = new Funcionario(nome, cargo, salario);
            result = dbHelperFunc.inserirNoBanco(f);
            if(result != -1){
                alert("Funcionário cadastrado com sucesso!");
            }else{
                alert("Ocorreu um erro, por favor tente novamente.");
            }
        }
        finish();
    }

    private boolean isNull(){
        return nomeFunc.getText().toString().isEmpty() ||
                salarioFunc.getText().toString().isEmpty();
    }

    private void alert (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarDados();
    }
}
