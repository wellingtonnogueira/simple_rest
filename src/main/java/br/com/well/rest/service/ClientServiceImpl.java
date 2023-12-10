package br.com.well.rest.service;

import br.com.well.rest.exception.ApplicationException;
import br.com.well.rest.helpers.JsonHelper;
import br.com.well.rest.service.model.ClientModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ClientServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
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

        return id; // TODO implement code
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
