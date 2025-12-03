package br.com.api.client.transaction.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum TipoTransacao {

    TRANSFERIR("T", true),
    CONSULTAR("C", false);

    private final String acao;
    private final boolean notificarBacen;


}
