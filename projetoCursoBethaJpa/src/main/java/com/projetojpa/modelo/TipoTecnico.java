package com.projetojpa.modelo;

/**
 *
 * @author lucionei.chequeto
 */
public enum TipoTecnico {

    T("TECNICO"), G("GERENTE");

    private final String tipoTecnico;

    TipoTecnico(String tipoTecnico) {
        this.tipoTecnico = tipoTecnico;
    }

    @Override
    public String toString() {
        return tipoTecnico;
    }

}
