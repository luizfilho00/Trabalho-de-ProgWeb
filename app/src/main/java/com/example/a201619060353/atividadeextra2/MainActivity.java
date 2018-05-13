package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrarCargo(View view) {
        Intent intent = new Intent(this, CadastroCargo.class);
        startActivity(intent);
    }

    public void cadastrarFuncionario(View view) {
        Intent intent = new Intent(this, CadastroFuncionario.class);
        startActivity(intent);
    }

    public void listarFuncionarios(View view) {
        Intent intent = new Intent(this, ListaFuncionarios.class);
        startActivity(intent);
    }
}
