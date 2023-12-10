package br.com.well.rest.service.model;

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
}
