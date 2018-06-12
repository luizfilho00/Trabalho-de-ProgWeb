package com.example.a201619060353.atividadeextra2;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a201619060353.atividadeextra2.dados.Usuario;
import com.example.a201619060353.atividadeextra2.modelo.Alert;
import com.example.a201619060353.atividadeextra2.modelo.NovoFingerPrint;
import com.example.a201619060353.atividadeextra2.modelo.PrimeiroLogin;
import com.example.a201619060353.atividadeextra2.modelo.UsuarioDAO;

public class Login extends AppCompatActivity {
    private EditText edtLogin, edtSenha;
    private UsuarioDAO bdUser;
    private Dialog solicitaScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        bdUser = new UsuarioDAO(this);
        if (existeUsuarioCadastrado())
            scanDialog();
    }

    public boolean existeUsuarioCadastrado(){
        return bdUser.getUser() != null;
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

    public void scanDialog(){
        System.out.println("CHEGOOOOOOOOOOOU AQUIIIIIIIIIIIIIIII");
        solicitaScan = new Dialog(this);
        solicitaScan.setContentView(R.layout.dialog_scan_layout);
        Button usarSenha = (Button) solicitaScan.findViewById(R.id.btnUsarSenhaD);
        Button cancelar = (Button) solicitaScan.findViewById(R.id.btnCancelarScanD);
        usarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitaScan.cancel();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitaScan.cancel();
            }
        });

        solicitaScan.show();
        NovoFingerPrint fingerPrint = new NovoFingerPrint(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickUsarDigital(View view) {
        if (!existeUsuarioCadastrado()){
            Alert.print(this, "Não existem usuários cadastrados ainda!");
            return;
        }
        scanDialog();
    }
}
