package br.com.api.client.transaction.service.impl;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.exception.DisableAccountException;
import br.com.api.client.transaction.exception.LimiteIndisponivelException;
import br.com.api.client.transaction.mapper.ClientResponseMapper;
import br.com.api.client.transaction.model.ClientResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.dto.DadosTransferenciaDTO;
import br.com.api.client.transaction.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final BacenClient bacenClient;

    @Override
    public ContaResponse validarConta(ContaResponse contaResponse, TipoTransacao transacao) {
        var activeAccount = contaResponse.getContaAtiva();
        DadosTransferencia transferencia = new DadosTransferencia();
        var valorTransferencias = 0;

        for (DadosTransferencia action : contaResponse.getTransferencias()) {
            transferencia = action;
            valorTransferencias += action.getSaida();
        }

        if(activeAccount){

            var limitDisponible = contaResponse.getSaldo() < valorTransferencias;

            if (limitDisponible){
                throw new LimiteIndisponivelException("Sem saldo Disponível");
            }
        } else {
            throw new DisableAccountException("Conta Desativada !");
        }

        if(transacao.isNotificarBacen()) {
            if (valorTransferencias > contaResponse.getLimiteDiario()) {
                throw new LimiteIndisponivelException("O seu limite diario é de até 1000 reais ");
            } else {
                this.bacenClient.notificarBacen(transferencia);
            }
        }

        return contaResponse;
    }

}
