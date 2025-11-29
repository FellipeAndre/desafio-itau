package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;

public interface CadastroClient {

    ClientResponse consultarCadastro(ClientRequest request);
}
