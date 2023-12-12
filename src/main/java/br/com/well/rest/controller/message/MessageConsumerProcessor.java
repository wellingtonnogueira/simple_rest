package br.com.well.rest.controller.message;

import br.com.well.rest.helpers.JsonHelper;
import br.com.well.rest.service.ClientService;
import br.com.well.rest.service.model.ClientModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

@Slf4j
@Configuration
// Code based on article https://medium.com/enterprise-java/spring-kafka-publish-messages-to-topic-consume-messages-from-topic-2905873dd107
public class MessageConsumerProcessor {

    private final ClientService clientService;

    @Autowired
    public MessageConsumerProcessor(ClientService clientService) {
        this.clientService = clientService;
    }

    @KafkaListener(topics = "review", groupId = "batch-id", containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<String> reviews, Acknowledgment ack) {
        log.info("Received review size {}", reviews.size());
        try {
            List<ClientModel> clientList = reviews.stream().map(this::deserialize).toList();
            log.info("Converted into track domain objects , total size {}", clientList.size());

            clientList.forEach(clientService::save);

        } catch (RuntimeException re) {
            log.error("Error consuming messages", re);
            throw re;
        } finally {
            log.info("acknowledge");
            ack.acknowledge();
        }
    }

    private ClientModel deserialize(String review) {
        try {
            JsonHelper<ClientModel> jsonHelper = new JsonHelper<>(ClientModel.class);
            return jsonHelper.stringToObject(review);
        } catch (JsonProcessingException e) {
            log.error("Error reading the message", e);
        }
        return null;
    }
}