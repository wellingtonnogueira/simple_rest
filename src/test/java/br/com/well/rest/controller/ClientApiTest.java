package br.com.well.rest.controller;

import br.com.well.rest.RestApplication;
import br.com.well.rest.exception.AppCustomRestExceptionHandler;
import br.com.well.rest.helpers.JsonHelper;
import br.com.well.rest.service.ClientService;
import br.com.well.rest.service.model.ClientModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@Slf4j
@AutoConfigureMockMvc
@ContextConfiguration(classes = RestApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ClientApiTest {

    private static final String URL = "";
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ClientApiController controller;

    @Mock
    private ClientService service;

    @BeforeEach
    public void setUp() {

        try(AutoCloseable openedMock = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders
                    .standaloneSetup(controller)
                    .setControllerAdvice(new AppCustomRestExceptionHandler())
                    .build();
        } catch (Exception e) {
            log.info("Error: " + e.getMessage(), e);

        }
    }

    @Test
    void findAll_success() throws Exception {

        List<ClientModel> defaultList = Arrays.asList(
                ClientModel.builder().fullName("Full Name I").description("Client I").build(),
                ClientModel.builder().fullName("Full Name II").description("Client II").build()
        );
        when(service.findAll()).thenReturn(defaultList);

        MvcResult mvcResult = mockMvc.perform(get(URL + "/clients/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        JsonHelper<ClientModel> jsonHelper = new JsonHelper<>(ClientModel.class);
        List<ClientModel> clientModelList = jsonHelper.stringToList(content);

        Assertions.assertEquals(defaultList.size(), clientModelList.size());
        Assertions.assertIterableEquals(defaultList, clientModelList);

        verify(service, times(1)).findAll();

    }

    @Test
    void findById_success() throws Exception {
        String id = UUID.randomUUID().toString();

        ClientModel returnedModel = ClientModel.builder().id(id).fullName("Full Name III").description("Client III").build();
        when(service.find(anyString())).thenReturn(returnedModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + "/clients/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        JsonHelper<ClientModel> jsonHelper = new JsonHelper<>(ClientModel.class);
        ClientModel clientModel = jsonHelper.stringToObject(content);

        Assertions.assertEquals(returnedModel.getFullName(), clientModel.getFullName());

        verify(service, times(1)).find(anyString());

    }

    @Test
    void newClient_success() throws Exception {
        String id = UUID.randomUUID().toString();

        ClientModel defaultClient = ClientModel.builder().fullName("Full Name IV").description("Client IV").build();

        when(service.postClientMessage(any(ClientModel.class))).thenReturn(id);

        JsonHelper<ClientModel> jsonHelper = new JsonHelper<>(ClientModel.class);

        String clientBody = jsonHelper.jsonToString(
                defaultClient
        );

        MvcResult mvcResult = mockMvc.perform(
                    post(URL + "/clients/")
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(clientBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("location");
        Assertions.assertNotNull(location);
        Assertions.assertTrue(location.contains(id));

        verify(service, times(1)).postClientMessage(any(ClientModel.class));

    }

    @Test
    void deleteClient_success() throws Exception {
        String id = UUID.randomUUID().toString();

        when(service.deleteClientMessage(anyString())).thenReturn(true);

        mockMvc.perform(
                    delete(URL + "/clients/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andReturn();

        verify(service, times(1)).deleteClientMessage(anyString());

    }

}
