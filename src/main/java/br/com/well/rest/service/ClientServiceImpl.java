package br.com.well.rest.service;

import br.com.well.rest.exception.InternalApplicationException;
import br.com.well.rest.exception.NotFoundException;
import br.com.well.rest.repository.ClientRepository;
import br.com.well.rest.repository.entity.ClientEntity;
import br.com.well.rest.service.model.ClientModel;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(ClientModel clientModel) {
        Optional<ClientEntity> found = repository.findById(clientModel.getId());
        if(found.isPresent()) {
            throw new InternalApplicationException(String.format("The client already exists. %s", clientModel.getId()));
        }
        repository.save(ClientModel.toEntity(clientModel));
    }

    @Override
    public List<ClientModel> findAll() {
        List<ClientEntity> entityList = repository.findAll();
        return entityList.stream().map(ClientModel::toModel).toList();

    }

    @Override
    @Transactional
    public boolean deleteClientMessage(String clientModelId) {
        Optional<ClientEntity> found = repository.findById(clientModelId);
        found.ifPresentOrElse(repository::delete, () -> {
            throw new NotFoundException("ID not found for deletion");
        });
        return true;
    }

    @Override
    public ClientModel find(String id) {
        Optional<ClientEntity> found = repository.findById(id);
        return found.map(ClientModel::toModel).orElseThrow(() -> new NotFoundException("It was not possible to find the ID provided"));
    }
}
