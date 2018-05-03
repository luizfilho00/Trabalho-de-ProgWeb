package com.example.a201619060353.atividadeextra2;


import java.io.Serializable;

/**
 * Created by 201619060353 on 02/05/2018.
 */

public class Funcionario implements Serializable {

    private int id;
    private String nome;
    private double salario;
    private int cargo;

    public Funcionario(int id, String nome, double salario){
        this.id = id;
        this.nome = nome;
        this.salario = salario;
    }

    public Funcionario(String nome, double salario){
        this.nome = nome;
        this.salario = salario;
    }

    public Funcionario(String nome, double salario, int cargo){
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

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString(){
        return nome + " - Cargo:" + cargo + " - Salário: R$" + salario;
    }
}
