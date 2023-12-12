package br.com.well.rest.service;

import br.com.well.rest.exception.ApplicationException;
import br.com.well.rest.helpers.JsonHelper;
import br.com.well.rest.repository.ClientRepository;
import br.com.well.rest.repository.entity.ClientEntity;
import br.com.well.rest.service.model.ClientModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String postClientMessage(ClientModel clientModelRequest) {

        log.info("[START] Will post a message onto KAFKA: " + clientModelRequest);

        //Need to check if the client already exists before continue.
        //If exists, then throw an exception.
        String id = UUID.randomUUID().toString();

        // TODO create a Model for messaging exchange and encapsulate ClientModel into it.
        ClientModel clientModel = ClientModel.builder()
                .id(id)
                .fullName(clientModelRequest.getFullName())
                .description(clientModelRequest.getDescription())
                .build();

        try {
            JsonHelper<ClientModel> jsonHelper = new JsonHelper<>(ClientModel.class);
            String clientMessage = jsonHelper.jsonToString(clientModel);

            log.info("Client Message: " + clientMessage);

            kafkaTemplate.send("review", clientMessage);
        } catch (JsonProcessingException e) {
            log.error("Error when sending message to kafka");
            throw new ApplicationException("Communication failed", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return id;
    }

    @Override
    @Transactional
    public boolean save(ClientModel clientModel) {
        Optional<ClientEntity> found = repository.findById(clientModel.getId());
        if(found.isEmpty()) {
            repository.save(ClientModel.toEntity(clientModel));
            return true;
        }
        return false;
    }

    @Override
    public List<ClientModel> findAll() {
        List<ClientEntity> entityList = repository.findAll();
        return entityList.stream().map(ClientModel::toModel).toList();

    }

    @Override
    @Transactional
    public boolean deleteClientMessage(String clientModelId) {
        Optional<ClientEntity> found = repository.findById(clientModelId);
        found.ifPresent(repository::delete);
        return true;
    }

    @Override
    public ClientModel find(String id) {
        Optional<ClientEntity> found = repository.findById(id);
        return found.map(ClientModel::toModel).orElse(null);
    }

}
