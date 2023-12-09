package br.com.well.rest.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClientModel {
    @JsonIgnore
    private String id; // Not exposed as JSon
    private String fullName;
    private String description;
}
