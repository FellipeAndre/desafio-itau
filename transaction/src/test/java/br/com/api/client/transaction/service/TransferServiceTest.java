package br.com.api.client.transaction.service;

import br.com.api.client.transaction.client.impl.BacenClientImpl;
import br.com.api.client.transaction.client.impl.CadastroClientImpl;
import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.service.impl.TransferService;
import br.com.api.client.transaction.service.impl.ContaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @InjectMocks
    TransferService conectClientService;

    @Mock
    BacenClientImpl bacenClient;

    @Mock
    CadastroClientImpl cadastroClient;

    @Mock
    ContaServiceImpl contaService;

    @Test
    void getClient() {

        var cpf = "44653378800";
        var response = new ClientResponse();
        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(40.0);
        dados.setNumeroConta("0001");
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        conta.setSaldo(4000.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);
        response.setNome("teste");
        response.setContaResponse(conta);

        Mockito.when(cadastroClient.consultarSaldo(Mockito.anyString())).thenReturn(response);

        var result = conectClientService.consultarSaldo(cpf);

        Assertions.assertNotNull(result);

        Mockito.verify(contaService, Mockito.times(1))
                        .validarConta(conta, TipoTransacao.CONSULTAR);
        Mockito.verify(cadastroClient, Mockito.times(1))
                .consultarSaldo(cpf);
    }
}