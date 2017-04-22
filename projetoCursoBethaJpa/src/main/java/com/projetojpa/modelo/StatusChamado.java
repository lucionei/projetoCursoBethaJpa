package com.projetojpa.modelo;

/**
 *
 * @author lucionei.chequeto
 */
public enum StatusChamado {
    A("ABERTO"), P("APROVADO"), C("CANCELADO"), F("FINALIZADO");

    private final String status;

    StatusChamado(String valorStatus) {
        status = valorStatus;
    }

    public String toString(){
        return status;
    }
}
