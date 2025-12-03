package br.com.api.client.transaction.service.impl;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.client.CadastroClient;
import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.mapper.ClientResponseMapper;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import br.com.api.client.transaction.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.api.client.transaction.enumerable.TipoTransacao.TRANSFERIR;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CadastroClient cadastroClient;
    private final ContaService contaService;
    private final ClientResponseMapper mapper;

    public ClientResponse consultarSaldo(String cpf) {
        var response = cadastroClient.consultarSaldo(cpf);

        var extrato = contaService.validarConta(response.getContaResponse(), TipoTransacao.CONSULTAR);
        response.setContaResponse(extrato);

        return response;
    }

    public ContaResponse transferir(String cpf, DadosTransferenciaDTO dadosTransferencia) {

        var response = cadastroClient.consultarSaldo(cpf);
        ContaResponse conta = null;

        if(!Objects.isNull(response)){
            conta = mapper.getContaResponse(dadosTransferencia);
        }

        return contaService.validarConta(conta, TRANSFERIR);
    }
}
