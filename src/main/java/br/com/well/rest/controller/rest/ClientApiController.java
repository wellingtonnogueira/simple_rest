package br.com.well.rest.controller.rest;

import br.com.well.rest.service.ClientMessageService;
import br.com.well.rest.service.ClientService;
import br.com.well.rest.service.model.ClientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@Slf4j
public class ClientApiController implements ClientApi {

    private final ClientService service;
    private final ClientMessageService messageService;

    @Autowired
    public ClientApiController(ClientService clientService, ClientMessageService messageService) {
        this.service = clientService;
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<List<ClientModel>> getClients() {
        // TODO add pagination

        return ResponseEntity.ok().body(service.findAll());

    }

    @Override
    public ResponseEntity<ClientModel> getClient(String id) {
        return ResponseEntity.ok().body(service.find(id));
    }

    @Override
    public ResponseEntity<Object> newClient(ClientModel clientModel) {

        String id = messageService.postClientMessage(clientModel);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Object> deleteClient(String id) {
        service.deleteClientMessage(id);
        return ResponseEntity.noContent().build();
    }
}
