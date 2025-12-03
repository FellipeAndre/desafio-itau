package br.com.api.client.transaction.service;

import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.model.ContaResponse;

public interface ContaService {

    ContaResponse validarConta(ContaResponse contaResponse, TipoTransacao transacao);
}
