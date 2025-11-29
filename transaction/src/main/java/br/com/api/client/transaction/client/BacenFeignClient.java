package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.RequestBacen;
import br.com.api.client.transaction.model.RetornoBacen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "api-bacen", url = "http//:localhost:8082", path = "/notificar")
public interface BacenFeignClient {

    @PostMapping
    RetornoBacen notificar(RequestBacen requestBacen);
}
