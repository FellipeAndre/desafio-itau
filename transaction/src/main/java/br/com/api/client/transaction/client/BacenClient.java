package br.com.api.client.transaction.client;

import br.com.api.client.transaction.model.RequestBacen;
import br.com.api.client.transaction.model.RetornoBacen;

public interface BacenClient{

    RetornoBacen notificarBacen(RequestBacen requestBacen);

}
