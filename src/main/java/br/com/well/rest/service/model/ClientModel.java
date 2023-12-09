package br.com.well.rest.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ClientModel {
    @JsonIgnore
    private String id; // Not exposed as JSon
    @JsonProperty("fullName") private String fullName;
    @JsonProperty("description") private String description;
}
