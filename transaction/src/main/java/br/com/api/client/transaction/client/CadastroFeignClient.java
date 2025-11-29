package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "api-cadastro", url = "http/localhost:8081", path = "/consultar-client")
public interface CadastroFeignClient {

    @GetMapping
    ClientResponse consultar(ClientRequest request);

}
