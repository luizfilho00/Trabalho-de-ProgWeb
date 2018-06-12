package com.example.a201619060353.atividadeextra2.dados;


import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by 201619060353 on 02/05/2018.
 */

public class Funcionario implements Serializable {

    private int id;
    private String nome;
    private double salario;
    private Cargo cargo;
    private String dataPagamento;
    private int cargoID;
    private String cargoNome;

    public Funcionario(int id, String nome, Cargo cargo, String dataPagamento, double salario){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.dataPagamento = dataPagamento;
        this.salario = salario;
    }

    public Funcionario(String nome, Cargo cargo, String dataPagamento, double salario){
        this.nome = nome;
        this.cargo = cargo;
        this.dataPagamento = dataPagamento;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }
    public double getSalario() {
        return salario;
    }

    public Cargo getCargo() { return cargo; }

    public void setCargo(Cargo cargo){ this.cargo = cargo; }

    @Override
    public String toString() {
        return nome + "_" + cargo.getNomeDoCargo()
                + "_" + dataPagamento + "_" + "R$"+ new DecimalFormat(".00").format(salario);
    }
}
