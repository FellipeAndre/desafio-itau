package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.CadastroClient;
import br.com.api.client.transaction.client.CadastroFeignClient;
import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastroClientImpl implements CadastroClient {

    private final CadastroFeignClient cadastroClient;

    //TODO CRIAR CIRCUT BREAK PARA CENARIOS DE FALHA
    @Override
    public ClientResponse consultarCadastro(String cpf) {
        return cadastroClient.consultar(cpf);
    }

    public void clientFallBack(){

    }
}
