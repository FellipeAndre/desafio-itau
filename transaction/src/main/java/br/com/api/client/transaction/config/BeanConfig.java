package br.com.api.client.transaction.config;

import br.com.api.client.transaction.client.BacenFeignClient;
import br.com.api.client.transaction.client.impl.BacenClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BacenClientImpl bacenClient(BacenFeignClient bacenFeingClient){
        return new BacenClientImpl(bacenFeingClient);
    }
}
