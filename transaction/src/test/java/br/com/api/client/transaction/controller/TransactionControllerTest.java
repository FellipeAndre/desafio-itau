package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.service.ConectClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    TransactionController controller;

    @Mock
    ConectClientService conectClientService;

    @Test
    void getClientCpf() {

        var cpf = "44815288809";

        var response = controller.getClientCpf(cpf);

         Mockito.verify(conectClientService, Mockito.times(1)).getClient(Mockito.anyString());

        Assertions.assertNotNull(response);
    }
}