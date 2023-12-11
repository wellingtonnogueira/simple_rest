package br.com.well.rest;

import br.com.well.rest.controller.rest.ClientApiController;
import br.com.well.rest.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestApplicationTests {

	private final ClientApiController myController;

	private final ClientServiceImpl myService;

	@Autowired
	public RestApplicationTests(ClientApiController myController, ClientServiceImpl myService) {
		this.myController = myController;
		this.myService = myService;
	}

	@Test
	void contextLoads() {
		assertThat(myController).isNotNull();
		assertThat(myService).isNotNull();
	}

}
