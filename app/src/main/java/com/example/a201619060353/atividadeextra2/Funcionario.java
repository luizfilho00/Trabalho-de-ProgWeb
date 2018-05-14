package com.example.a201619060353.atividadeextra2;


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
    private int cargoID;
    private String cargoNome;

    public Funcionario(int id, String nome, Cargo cargo, double salario){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public Funcionario(String nome, Cargo cargo, double salario){
        this.nome = nome;
        this.salario = salario;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Cargo getCargo() { return cargo; }

    public void setCargo(Cargo cargo){ this.cargo = cargo; }

    @Override
    public String toString() {
        return nome + "_" + cargo.getNomeDoCargo()
                + "_" + "R$"+ new DecimalFormat(".00").format(salario);
    }
}
