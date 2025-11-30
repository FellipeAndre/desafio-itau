package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.model.ClientResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "api-cadastro", url = "http/localhost:8081", path = "/consultar-client")
public interface CadastroFeignClient {

    @GetMapping("/{cpf}")
    ClientResponse consultar(@PathVariable @Valid String cpf);

}
