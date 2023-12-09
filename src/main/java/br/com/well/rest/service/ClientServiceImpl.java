package br.com.well.rest.service;

import br.com.well.rest.service.model.ClientModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public String postClientMessage(ClientModel clientModel) {
        return UUID.randomUUID().toString(); // TODO implement code
    }

    @Override
    public List<ClientModel> findAll() {
        //TODO change it to query from database...

        return Arrays.asList(
                ClientModel.builder().description("aaa").build(),
                ClientModel.builder().description("bbb").build()
        );
    }

    @Override
    public boolean deleteClientMessage(String clientModelId) {
        return true; // TODO implement code
    }

    @Override
    public ClientModel find(String id) {
        //TODO change it to query from database...

        return ClientModel.builder().description("aaa").build();
    }

}
