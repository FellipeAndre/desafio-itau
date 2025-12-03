package br.com.api.client.transaction.service.impl;

import br.com.api.client.transaction.client.BacenClient;
import br.com.api.client.transaction.enumerable.TipoTransacao;
import br.com.api.client.transaction.exception.DisableAccountException;
import br.com.api.client.transaction.exception.ErroBacenIndisponivelException;
import br.com.api.client.transaction.exception.LimiteIndisponivelException;
import br.com.api.client.transaction.model.BacenResponse;
import br.com.api.client.transaction.model.ContaResponse;
import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final BacenClient bacenClient;
    private final static Integer ERRO_BACEN = 429;

    @Override
    public ContaResponse validarConta(ContaResponse contaResponse, TipoTransacao transacao) {
        var activeAccount = contaResponse.getContaAtiva();
        List<DadosTransferencia> transferencia = new ArrayList<>();
        var valorTransferencias = 0.0;

        if(activeAccount){

            for (DadosTransferencia action : contaResponse.getTransferencias()) {
                transferencia.add(action);
                valorTransferencias = action.getSaida();
            }
            contaResponse.setTransferencias(transferencia);
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
                BacenResponse bacen = bacenClient.notificarBacen(transferencia.get(0));
               checarResponseBacen(bacen);
               contaResponse.getTransferencias().get(0).setResponseBacen(bacen);
            }
        }

        return contaResponse;
    }

    private void checarResponseBacen(BacenResponse bacen){
        if(bacen.getCod().equals(ERRO_BACEN)){
            throw new ErroBacenIndisponivelException(bacen.getMessage());
        }
    }



}
