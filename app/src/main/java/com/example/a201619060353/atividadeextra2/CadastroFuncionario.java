package com.example.a201619060353.atividadeextra2;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    BDHelper bdHelper = new BDHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        nomeFunc = findViewById(R.id.edtNomeFunc);
        salarioFunc = findViewById(R.id.edtSalario);
        spinnerCargo = findViewById(R.id.spinCargo);
        ArrayList<Cargo> listCargos = bdHelper.selectAllCargo();
        listCargos.sort(new Comparator<Cargo>() {
            @Override
            public int compare(Cargo cargo, Cargo t1) {
                return cargo.getNomeDoCargo().compareToIgnoreCase(t1.getNomeDoCargo());
            }
        });
        ArrayAdapter<Cargo> listCargoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listCargos);
        listCargoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(listCargoAdapter);

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
        if (spinnerCargo == null || spinnerCargo.getSelectedItem() == null){
            alert("Por favor cadastre um Cargo antes.");
            return;
        }
        if (isNull()){
            alert("Por favor preencha todos os campos!");
            return;
        }
        String nome = nomeFunc.getText().toString();
        double salario = Double.valueOf(salarioFunc.getText().toString());
        String cargoNome = spinnerCargo.getSelectedItem().toString();
        int cargoId = bdHelper.buscarCargo(cargoNome);
        Cargo cargo = new Cargo(cargoId, cargoNome);
        System.out.println("CARGOOOOOOOOOOOOOOOOOOOO: " + cargo.getNomeDoCargo());
        Funcionario f;
        long result;

        if (getIntent().getStringExtra("funcao") != null){
            if (getIntent().getStringExtra("funcao").equals("atualizar")){
                f = new Funcionario(idFunc, nome, cargo, salario);
                result = bdHelper.alterarNoBanco(f);
                if(result != -1){
                    alert("Funcionário alterado com sucesso!");
                }else{
                    alert("Ocorreu um erro, por favor tente novamente.");
                }
            }
        }
        else{
            f = new Funcionario(nome, cargo, salario);
            result = bdHelper.inserirNoBanco(f);
            if(result != -1){
                alert("Funcionário inserido com sucesso!");
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
}
