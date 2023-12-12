package br.com.well.rest;

import br.com.well.rest.controller.message.MessageConsumerProcessor;
import br.com.well.rest.controller.rest.ClientApiController;
import br.com.well.rest.repository.ClientRepository;
import br.com.well.rest.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestApplicationTests {

	private final ClientApiController clientApiController;
	private final ClientServiceImpl clientService;
	private final MessageConsumerProcessor messageConsumerProcessor;
	private final ClientRepository clientRepository;

	@Autowired
	public RestApplicationTests(ClientApiController clientApiController, ClientServiceImpl clientService, MessageConsumerProcessor messageConsumerProcessor, ClientRepository clientRepository) {
		this.clientApiController = clientApiController;
		this.clientService = clientService;
		this.messageConsumerProcessor = messageConsumerProcessor;
		this.clientRepository = clientRepository;
	}

	@Test
	void contextLoads() {
		assertThat(clientRepository).isNotNull();
		assertThat(clientService).isNotNull();
		assertThat(messageConsumerProcessor).isNotNull();
		assertThat(clientApiController).isNotNull();

	}

}
