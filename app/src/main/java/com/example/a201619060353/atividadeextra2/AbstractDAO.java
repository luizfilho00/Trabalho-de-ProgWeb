package com.example.a201619060353.atividadeextra2;

import java.util.ArrayList;

public abstract class AbstractDAO<T> {

    abstract public long inserirNoBanco(T objeto);
    abstract public long alterarNoBanco(T objeto);
    abstract public long excluirDoBanco(T objeto);
    abstract public ArrayList<T> selectAll();
}
