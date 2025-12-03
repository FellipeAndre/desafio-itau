package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.ClientResponse;

public interface CadastroClient {

    ClientResponse consultarSaldo(String cpf);
}
