package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.client.BacenFeignClient;
import br.com.api.client.transaction.model.RequestBacen;
import br.com.api.client.transaction.model.RetornoBacen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BacenClientImpl implements BacenClient  {

    private final BacenFeignClient bacenFeingClient;

    //TODO AQUI TERÁ UM CIRCUIT BREAK PARA CASO O SISTEMA DO BACEN ESTEJA FORA
    @Override
    public RetornoBacen notificarBacen(RequestBacen requestBacen) {

        RetornoBacen response = bacenFeingClient.notificar(requestBacen);

        return response;
    }

    public String BacenFallback(){
        return "Sistema Indisponível";
    }
}
