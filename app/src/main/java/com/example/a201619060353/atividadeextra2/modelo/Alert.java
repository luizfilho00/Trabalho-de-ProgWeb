package com.example.a201619060353.atividadeextra2.modelo;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Alert{
    public static void print(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
