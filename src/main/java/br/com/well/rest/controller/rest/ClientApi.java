package br.com.well.rest.controller.rest;
import br.com.well.rest.service.model.ClientModel;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Client"})
@SwaggerDefinition(tags =  @Tag(name = "Client", description = "Client endpoints"))
@RequestMapping("clients")
public interface ClientApi {

    @ApiOperation(value = "GetClientsList")
    @GetMapping("/")
    ResponseEntity<Page<ClientModel>> getClients(@RequestHeader int page, @RequestHeader int pageSize);

    @ApiOperation(value = "GetClient")
    @GetMapping("/{id}")
    ResponseEntity<ClientModel> getClient(@PathVariable String id);

    @ApiOperation(value = "PostClient")
    @PostMapping("/")
    ResponseEntity<Object> newClient(@RequestBody ClientModel clientModel);

    @ApiOperation(value = "DeleteClient")
    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteClient(@PathVariable String id);

}