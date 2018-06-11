package com.example.a201619060353.atividadeextra2;

import java.text.DecimalFormat;

public class Gasto {
    private int id;
    private String tipo;
    private String data;
    private int pago;
    private double valor;

    public Gasto(String tipo, String data, int pago, double valor){
        this.tipo = tipo;
        this.data = data;
        this.pago = pago;
        this.valor = valor;
    }

    public Gasto(int id, String tipo, String data, int pago, double valor){
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.pago = pago;
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

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    @Override
    public String toString() {
        return tipo + "_" + data + "_" + "R$" + new DecimalFormat(".00").format(valor);
    }
}
