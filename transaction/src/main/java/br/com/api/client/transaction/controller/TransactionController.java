package br.com.api.client.transaction.controller;

import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import br.com.api.client.transaction.service.impl.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-transfer")
@RequiredArgsConstructor
public class TransactionController {

    private final TransferService service;

     @GetMapping("consulta-saldo/{cpf}")
     public ResponseEntity<ClientResponse> getConsultarSaldo(@PathVariable @Valid String cpf){

        var result = service.consultarSaldo(cpf);

         return ResponseEntity.status(HttpStatus.OK).body(result);
     }

    @PostMapping("transferir/{cpf}")
    public ResponseEntity<ContaResponse> Traneferencia(@PathVariable @Valid String cpf, @RequestBody DadosTransferenciaDTO dadosTransferencia){

        var dadosclient = service.transferir(cpf, dadosTransferencia);

        return ResponseEntity.status(HttpStatus.OK).body(dadosclient);
    }
}
