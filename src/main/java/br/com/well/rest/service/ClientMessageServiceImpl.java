package br.com.well.rest.service;

import br.com.well.rest.exception.ApplicationException;
import br.com.well.rest.helpers.JsonHelper;
import br.com.well.rest.service.model.ClientMessageModel;
import br.com.well.rest.service.model.ClientModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ClientMessageServiceImpl implements ClientMessageService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ClientMessageServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String postClientMessage(ClientModel clientModelRequest) {

        log.info("[START] Will post a message onto KAFKA: " + clientModelRequest);

        //Need to check if the client already exists before continue.
        //If exists, then throw an exception.
        String id = UUID.randomUUID().toString();

        ClientModel clientModel = ClientModel.builder()
                .id(id)
                .fullName(clientModelRequest.getFullName())
                .description(clientModelRequest.getDescription())
                .build();

        ClientMessageModel message = ClientMessageModel.createMessage(clientModel);

        try {
            JsonHelper<ClientMessageModel> jsonHelper = new JsonHelper<>(ClientMessageModel.class);
            String clientMessage = jsonHelper.jsonToString(message);

            log.info("Client Message: " + clientMessage);

            kafkaTemplate.send("review", clientMessage);
        } catch (JsonProcessingException e) {
            log.error("Error when sending message to kafka");
            throw new ApplicationException("Communication failed", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return id;
    }

}
