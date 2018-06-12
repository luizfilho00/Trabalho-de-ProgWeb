package com.example.a201619060353.atividadeextra2.dados;

import java.io.Serializable;

/**
 * Created by 201619060353 on 02/05/2018.
 */

public class Cargo implements Serializable{

    private int id;
    private String nomeDoCargo;

    public Cargo(int id, String nomeDoCargo){
        this.id = id;
        this.nomeDoCargo = nomeDoCargo;
    }

    public Cargo (String nomeDoCargo){
        this.nomeDoCargo = nomeDoCargo;
    }

    public String getNomeDoCargo() {
        return nomeDoCargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nomeDoCargo;
    }
}
