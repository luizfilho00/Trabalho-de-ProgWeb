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
    private int cargoID;
    private String cargoNome;

    public Funcionario(int id, String nome, int cargoID, String cargoNome, double salario){
        this.id = id;
        this.nome = nome;
        this.cargoID = cargoID;
        this.cargoNome = cargoNome;
        this.salario = salario;
    }

    public Funcionario(String nome, double salario, int cargoID, String cargoNome){
        this.nome = nome;
        this.salario = salario;
        this.cargoID = cargoID;
        this.cargoNome = cargoNome;
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

    public int getCargoID() {
        return cargoID;
    }

    public void setCargoID(int cargoID) {
        this.cargoID = cargoID;
    }

    @Override
    public String toString(){
        return nome + "  ::  Cargo: " + cargoNome
                + "  ::  Salário: R$" + new DecimalFormat(".00").format(salario);
    }

    public String getCargoNome() {
        return cargoNome;
    }

    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }
}
