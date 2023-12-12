package br.com.well.rest.service.model;

import br.com.well.rest.repository.entity.ClientEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ClientModel {
    @JsonProperty("id") private String id;
    @JsonProperty("fullName") private String fullName;
    @JsonProperty("description") private String description;

    public static ClientModel toModel(ClientEntity entity) {
        return ClientModel.builder()
                .id(entity.getId())
                .fullName(entity.getFullname())
                .description(entity.getDescription())
                .build();
    }

    public static ClientEntity toEntity(ClientModel model) {
        return ClientEntity.builder()
                .id(model.getId())
                .fullname(model.getFullName())
                .description(model.getDescription())
                .build();
    }
}
