package com.projetojpa.modelo;

/**
 *
 * @author lucionei.chequeto
 */
public enum TipoChamado {

    I("INTERNO"), E("EXTERNO");

    private final String tipo;

    TipoChamado(String valorTipo) {
        tipo = valorTipo;
    }

    @Override
    public String toString() {
        return tipo;
    }

}
