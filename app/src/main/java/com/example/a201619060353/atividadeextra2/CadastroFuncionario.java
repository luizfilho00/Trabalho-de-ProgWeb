package com.example.a201619060353.atividadeextra2;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroFuncionario extends AppCompatActivity {
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
        ArrayList<Cargo> cargos = bdHelper.selectAllCargo();
        ArrayAdapter<Cargo> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cargos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(adapter);
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
        String cargo = spinnerCargo.getSelectedItem().toString();
        int cargoId = bdHelper.buscarCargo(cargo);
        Funcionario f = new Funcionario(nome, salario, cargoId, cargo);
        long result = bdHelper.inserirNoBanco(f);
        if (result == -1)
            alert("Ocorreu um erro ao inserir novo funcionário, por favor tente novamente.");
        else
            alert("Funcionário cadastrado com sucesso!");
    }

    private boolean isNull(){
        return nomeFunc.getText().toString().isEmpty() ||
                salarioFunc.getText().toString().isEmpty();
    }

    private void alert (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
