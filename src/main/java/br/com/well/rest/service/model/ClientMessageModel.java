package br.com.well.rest.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ClientMessageModel {
    @JsonProperty("client") private ClientModel client;
    @JsonProperty("messageId") private String messageId;
    @JsonProperty("messageCreatedAt") private LocalDateTime messageCreatedAt;

    public static ClientMessageModel createMessage(ClientModel clientModel) {
        return ClientMessageModel.builder()
                .client(clientModel)
                .messageCreatedAt(LocalDateTime.now(ZoneOffset.UTC))
                .messageId(UUID.randomUUID().toString())
                .build();
    }
}