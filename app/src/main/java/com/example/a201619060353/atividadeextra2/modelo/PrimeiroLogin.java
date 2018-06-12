package com.example.a201619060353.atividadeextra2.modelo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.a201619060353.atividadeextra2.R;
import com.example.a201619060353.atividadeextra2.dados.Usuario;

public class PrimeiroLogin extends AppCompatActivity {
    private EditText edtLogin, edtSenha, edtConfirmaSenha;
    private UsuarioDAO bdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro_login);
        edtLogin = findViewById(R.id.edtLoginPL);
        edtSenha = findViewById(R.id.edtSenhaPL);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenhaPL);
        bdUser = new UsuarioDAO(this);
    }

    public void onClickAlterarPrimeiroLogin(View view) {
        if (isNull()) {
            Alert.print(this, "Por favor preencha todos os campos.");
            return;
        }
        if (!edtSenha.getText().toString().equals(edtConfirmaSenha.getText().toString())){
            Alert.print(this, "As senhas não coincidem!");
            return;
        }
        if (edtLogin.getText().length() > 20){
            Alert.print(this, "O nome de usuário não pode ultrapassar 20 caracteres!");
            return;
        }
        if (edtSenha.getText().length() < 8){
            Alert.print(this, "A senha deve ter no mínimo 8 caracteres!");
            return;
        }
        if (edtSenha.getText().length() > 12){
            Alert.print(this, "A senha deve ter no máximo 12 caracteres!");
            return;
        }
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();
        long result = bdUser.inserirNoBanco(new Usuario(login, senha));
        if (result == -1){
            Alert.print(this, "Ocorreu um erro ao alterar o usuário, por favor tente novamente.");
        }
        else
            Alert.print(this, "Usuário alterado com sucesso!");

        finish();
    }

    private boolean isNull(){
        return edtConfirmaSenha.getText().toString().isEmpty() ||
                edtSenha.getText().toString().isEmpty() ||
                edtLogin.getText().toString().isEmpty();
    }
}
