package com.example.a201619060353.atividadeextra2.modelo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import com.example.a201619060353.atividadeextra2.MainActivity;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context appContext;
    private boolean logado = false;

    public FingerprintHandler(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Alert.print(appContext, "Erro de autenticação\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Alert.print(appContext, "Ajuda para autenticação\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        Alert.print(appContext, "Falha na autenticação.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Alert.print(appContext,"Autenticado com sucesso!");
        appContext.startActivity(new Intent(appContext, MainActivity.class));
        logado = true;
    }

    public boolean getLogado(){ return logado; }
}