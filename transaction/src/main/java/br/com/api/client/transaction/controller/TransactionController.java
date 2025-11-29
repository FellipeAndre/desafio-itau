package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.model.ClientRequest;
import br.com.api.client.transaction.service.ConectClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-transfer")
@RequiredArgsConstructor
public class TransactionController {

    private ConectClientService conectClientService;

     @GetMapping
     public String teste(@RequestBody @Valid ClientRequest request){

         //conectClientService.getClient("teste");

         return "Seja bem vindo a Api de consultar e notificar";
     }
}
