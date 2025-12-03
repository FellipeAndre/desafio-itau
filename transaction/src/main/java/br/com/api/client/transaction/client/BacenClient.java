package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.DadosTransferencia;
import br.com.api.client.transaction.model.RetornoBacen;

public interface BacenClient{

    RetornoBacen notificarBacen(DadosTransferencia requestBacen);

}
