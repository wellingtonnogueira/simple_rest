package br.com.well.rest.service;

import br.com.well.rest.service.model.ClientModel;
import org.springframework.data.domain.Page;

public interface ClientService {
    void save(ClientModel clientModel);

    Page<ClientModel> findAllPaged(int page, int pageSize);

    boolean deleteClientMessage(String clientModelId);

    ClientModel find(String id);
}
