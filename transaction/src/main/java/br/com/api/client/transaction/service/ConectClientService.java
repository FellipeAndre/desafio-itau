package br.com.api.client.transaction.service;

import br.com.api.client.transaction.client.impl.BacenClientImpl;
import br.com.api.client.transaction.client.impl.CadastroClientImpl;
import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.RequestBacen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConectClientService{

    private final BacenClientImpl bacenClient;
    private final CadastroClientImpl cadastroClient;

    public void getClient(String nome){
        ClientRequest request = new ClientRequest();
        cadastroClient.consultarCadastro(request);

        RequestBacen requestBacen = new RequestBacen();
        this.bacenNotify(requestBacen);
    }

    private void bacenNotify(RequestBacen requestBacen){
        bacenClient.notificarBacen(requestBacen);
    }

}
