package com.example.a201619060353.atividadeextra2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroCargo extends AppCompatActivity {
    private EditText nomeDoCargo;
    private BDHelper bdHelperCargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cargo);
        nomeDoCargo = findViewById(R.id.edtNomeCargo);
        bdHelperCargo = new BDHelper(this);
    }

    public void salvarCargo(View view) {
        if (nomeDoCargo == null || nomeDoCargo.getText().toString().trim().equals("")){
            alert("Favor inserir nome do cargo!");
            return;
        }
        String nomeCargo = nomeDoCargo.getText().toString();
        Cargo c = new Cargo(nomeCargo);
        long result = bdHelperCargo.inserirNoBanco(c);
        if (result == -1)
            alert("Cargo já existe no banco!");
        else
            alert("Cargo cadastrado com sucesso!");
    }

    private void alert(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
