package br.com.well.rest.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ApiError {
 
    private HttpStatus status;
    private String message;
    private List<String> errors;
 
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
 
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }
}