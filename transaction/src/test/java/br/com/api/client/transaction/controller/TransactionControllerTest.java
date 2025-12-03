package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import br.com.api.client.transaction.service.impl.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransferService service;

    @Test
    void getClientCpf() throws Exception {

        ClientResponse response = new ClientResponse();
        ContaResponse contaResponse = new ContaResponse();
        DadosTransferencia transferencia = new DadosTransferencia();
        contaResponse.setContaAtiva(true);
        contaResponse.setSaldo(4000.0);
        contaResponse.setLimiteDiario(1000);
        contaResponse.setTransferencias(List.of(transferencia));
        response.setNome("teste");
        response.setContaResponse(contaResponse);

        Mockito.when(service.consultarSaldo(Mockito.anyString()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api-transfer/consulta-saldo/12345678900"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contaResponse.saldo").exists());
    }

    @Test
    void transferir() throws Exception {

        ContaResponse contaResponse = new ContaResponse();
        DadosTransferencia transferencia = new DadosTransferencia();
        contaResponse.setContaAtiva(true);
        contaResponse.setSaldo(4000.0);
        contaResponse.setLimiteDiario(1000);
        contaResponse.setTransferencias(List.of(transferencia));

        ObjectMapper mapper = new ObjectMapper();
        var request = mapper.writeValueAsString(getDadosTransferenciaDto());


        Mockito.when(service.transferir(Mockito.anyString(), Mockito.any(DadosTransferenciaDTO.class)))
                .thenReturn(contaResponse);
        String cpfTeste = "12345678900";

        mockMvc.perform(MockMvcRequestBuilders.post("/api-transfer/transferir/{cpf}", cpfTeste)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contaAtiva").value(true));
    }

    private DadosTransferenciaDTO getDadosTransferenciaDto() {
        var dados = new DadosTransferenciaDTO();
        dados.setNome("teste");
        dados.setSaida(80.0);
        dados.setNumeroConta("0002-2");

        return dados;
    }
}