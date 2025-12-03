package br.com.api.client.transaction.client.impl;

import br.com.api.client.transaction.client.CadastroClient;
import br.com.api.client.transaction.client.CadastroFeignClient;
import br.com.api.client.transaction.mapper.ClientResponseMapper;
import br.com.api.client.transaction.model.ClientResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastroClientImpl implements CadastroClient {

    private final CadastroFeignClient cadastroClient;
    private final ClientResponseMapper mapper;

    @CircuitBreaker(name = "default", fallbackMethod = "clientFallBack")
    @Retry(name = "default")
    @Override
    public ClientResponse consultarSaldo(String cpf) {
        return cadastroClient.consultar(cpf);
    }

    public ClientResponse clientFallBack(String cpf, Throwable ex){
        return mapper.getClientResponse();
    }

}
