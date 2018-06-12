package com.example.a201619060353.atividadeextra2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroCargo extends AppCompatActivity {
    private EditText nomeDoCargo;
    private CargoDAO dbHelperCargo;
    private int idCargo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cargo);

        dbHelperCargo = new CargoDAO(this);

        nomeDoCargo = findViewById(R.id.edtNomeCargo);
        if (getIntent().getStringExtra("funcao") != null){
            if(getIntent().getStringExtra("funcao").equals("atualizar")){
                Bundle b = getIntent().getExtras();
                Cargo c = (Cargo) b.getSerializable("chave_cargo");
                idCargo = c.getId();
                nomeDoCargo.setText(c.getNomeDoCargo());
                ((Button) findViewById(R.id.btnSalvarCargo)).setText("Alterar");
            }
        }
    }

    public void salvarCargo(View view) {
        if (nomeDoCargo == null || nomeDoCargo.getText().toString().trim().equals("")){
            Alert.print(this, "Favor inserir nome do cargo!");
            return;
        }
        Cargo c;
        long result;
        if (getIntent().getStringExtra("funcao") != null) {
            if (getIntent().getStringExtra("funcao").equals("atualizar")) {
                c = new Cargo(idCargo, nomeDoCargo.getText().toString());
                result = dbHelperCargo.alterarNoBanco(c);
                if(result != -1){
                    Alert.print(this, "Cargo alterado com sucesso!");
                }else{
                    Alert.print(this, "Ocorreu um erro, por favor tente novamente.");
                }
            }
        }
        else{
            c = new Cargo(idCargo, nomeDoCargo.getText().toString());
            result = dbHelperCargo.inserirNoBanco(c);
            if(result != -1){
                Alert.print(this, "Cargo cadastrado com sucesso!");
            }else{
                Alert.print(this, "Ocorreu um erro, por favor tente novamente.");
            }
        }
        finish();
    }
}
