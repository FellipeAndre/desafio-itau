package br.com.api.client.transaction.mapper;

import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientResponseMapper {

    public ClientResponse getClientResponse(){

        var client = new ClientResponse();
        var accountClient = new ContaResponse();
        accountClient.setContaAtiva(Boolean.TRUE);
        accountClient.setSaldo(3000.0);
        client.setContaResponse(accountClient);
        client.setNome("Felipe Tobias");

        return client;
    }

    public ContaResponse getContaResponse(DadosTransferenciaDTO dadosTransferencia){

        var accountClient = new ContaResponse();
        accountClient.setContaAtiva(Boolean.TRUE);
        accountClient.setSaldo(3000.0);
        var transferencia = new DadosTransferencia();
        transferencia.setNumeroConta(dadosTransferencia.getNumeroConta());
        transferencia.setSaida(dadosTransferencia.getSaida());
        transferencia.setNome(dadosTransferencia.getNome());
        List<DadosTransferencia> listTransferencia = new ArrayList<>();
        listTransferencia.add(transferencia);
        accountClient.setTransferencias(listTransferencia);

        return accountClient;
    }
}
