package br.com.api.client.transaction.service;

import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.exception.DisableAccountException;
import br.com.api.client.transaction.exception.LimiteIndisponivelException;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.service.impl.ContaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    ContaServiceImpl contaService;

    @Test
    void transferirContaAtiva() {

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

        var result = contaService.validarConta(conta, TipoTransacao.CONSULTAR);


        Assertions.assertNotNull(result);

    }

    @Test
    void transferirContaAtivaSaldoInsulficiente() {

        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(40.0);
        dados.setNumeroConta("0001");
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        conta.setSaldo(30.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);


        Assertions.assertThrows(LimiteIndisponivelException.class, () ->{
            contaService.validarConta(conta, TipoTransacao.CONSULTAR);
        });

    }

    @Test
    void transferirContaDesativada() {

        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(40.0);
        dados.setNumeroConta("0001");
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        conta.setSaldo(30.0);
        conta.setContaAtiva(false);
        conta.setTransferencias(dadosTransferencias);


        Assertions.assertThrows(DisableAccountException.class, () ->{
            contaService.validarConta(conta, TipoTransacao.CONSULTAR);
        });

    }

    @Test
    void transferirContaAtivaLimiteDiario() {

        var response = new ClientResponse();
        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        dados.setNome("teste");
        dados.setSaida(1000.54);
        dados.setNumeroConta("0001");
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        conta.setSaldo(0.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);
        response.setNome("teste");
        response.setContaResponse(conta);

        var result = contaService.validarConta(conta, TipoTransacao.CONSULTAR);


        Assertions.assertThrows(LimiteIndisponivelException.class, () ->{
            contaService.validarConta(conta, TipoTransacao.CONSULTAR);
        });

    }
}