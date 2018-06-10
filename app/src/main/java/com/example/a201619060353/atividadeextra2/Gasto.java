package com.example.a201619060353.atividadeextra2;

import java.text.DecimalFormat;

public class Gasto {
    private int id;
    private String tipo;
    private double valor;

    public Gasto(String tipo, double valor){
        this.tipo = tipo;
        this.valor = valor;
    }

    public Gasto(int id, String tipo, double valor){
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Gasto(String tipo){
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return tipo + "_" + "R$" + new DecimalFormat(".00").format(valor);
    }
}
