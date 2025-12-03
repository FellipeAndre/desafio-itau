package br.com.api.client.transaction.service;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.exception.DisableAccountException;
import br.com.api.client.transaction.exception.ErroBacenIndisponivelException;
import br.com.api.client.transaction.exception.LimiteIndisponivelException;
import br.com.api.client.transaction.model.BacenResponse;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.service.impl.ContaServiceImpl;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@SpringBootTest
@ActiveProfiles("test")
class ContaServiceTest {

    @Autowired
    ContaServiceImpl contaService;

    @MockBean
    BacenClient bacenClient;

    @Test
    void transferirContaAtiva() throws Exception {

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
        dadosTransferencias.add(dados);
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
        dadosTransferencias.add(dados);
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
        dadosTransferencias.add(dados);
        conta.setSaldo(0.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);
        response.setNome("teste");
        response.setContaResponse(conta);


        Assertions.assertThrows(LimiteIndisponivelException.class, () ->{
            contaService.validarConta(conta, TipoTransacao.CONSULTAR);
        });
    }

    @Test
    void transferirNotificarBacenException() {

        var response = new ClientResponse();
        var conta = new ContaResponse();
        var dados = new DadosTransferencia();
        var bacen = new BacenResponse("Sistema Insdisponivel", 429);

        dados.setNome("teste");
        dados.setSaida(40.0);
        dados.setNumeroConta("0001");
        dados.setResponseBacen(bacen);
        List<DadosTransferencia> dadosTransferencias = new ArrayList<>();
        dadosTransferencias.add(dados);
        conta.setSaldo(4000.0);
        conta.setContaAtiva(true);
        conta.setTransferencias(dadosTransferencias);
        response.setNome("teste");
        response.setContaResponse(conta);

        Assertions.assertThrows(ErroBacenIndisponivelException.class, () ->{
            contaService.validarConta(conta, TipoTransacao.TRANSFERIR);
        });

    }
}