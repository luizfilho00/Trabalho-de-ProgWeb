package com.example.a201619060353.atividadeextra2.modelo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.a201619060353.atividadeextra2.MainActivity;
import com.example.a201619060353.atividadeextra2.R;
import com.example.a201619060353.atividadeextra2.dados.Usuario;

public class Login extends AppCompatActivity {
    private EditText edtLogin, edtSenha;
    private UsuarioDAO bdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        bdUser = new UsuarioDAO(this);
    }

    public void onClickLogar(View view) {
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();
        if (isNull()) {
            Alert.print(this, "Por favor preencha todos os campos!");
            return;
        }
        Usuario user = bdUser.getUser();
        if (user == null) {
            if (login.equals("root") && senha.equals("root"))
                startActivity(new Intent(this, PrimeiroLogin.class));
            else
                Alert.print(this, "Para primeiro login entre com o usuário padrão!");
        }
        else{
            if (!user.getLogin().equals(login)) {
                Alert.print(this, "Usuário não encontrado!");
            }
            else{
                if (!user.getSenha().equals(senha))
                    Alert.print(this, "Senha incorreta!");
                else
                    startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    public boolean isNull(){
        return edtLogin.getText().toString().isEmpty() ||
                edtSenha.getText().toString().isEmpty();
    }
}
