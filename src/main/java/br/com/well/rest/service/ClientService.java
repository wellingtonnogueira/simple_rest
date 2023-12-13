package br.com.well.rest.service;

import br.com.well.rest.service.model.ClientModel;

import java.util.List;

public interface ClientService {
    void save(ClientModel clientModel);

    List<ClientModel> findAll();

    boolean deleteClientMessage(String clientModelId);

    ClientModel find(String id);
}
