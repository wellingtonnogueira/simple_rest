package br.com.well.rest.controller;
import br.com.well.rest.service.model.ClientModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Client"})
@SwaggerDefinition(tags =  @Tag(name = "Client", description = "Client endpoints"))
@RequestMapping("clients")
public interface ClientApi {

    @ApiOperation(value = "GetClientsList")
    @GetMapping("/")
    ResponseEntity<List<ClientModel>> getClients();

    @ApiOperation(value = "GetClient")
    @GetMapping("/{id}")
    ResponseEntity<ClientModel> getClient(@PathVariable String id);

    @ApiOperation(value = "PostClient")
    @PostMapping("/")
    ResponseEntity<Object> newClient(ClientModel clientModel);

    @ApiOperation(value = "DeleteClient")
    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteClient(@PathVariable String id);

}