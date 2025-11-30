package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.service.ConectClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-transfer")
@RequiredArgsConstructor
public class TransactionController {

    private final ConectClientService conectClientService;

     @GetMapping("/{cpf}")
     @ResponseStatus(HttpStatus.OK)
     public String getClientCpf(@PathVariable @Valid String cpf){

         conectClientService.getClient(cpf);

         return "Seja bem vindo a Api de consultar e notificar";
     }
}
