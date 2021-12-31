package com.bsf.services.accountsservice.api;

import com.bsf.services.accountsservice.api.bo.AccountData;
import com.bsf.services.accountsservice.exception.ServiceError;
import com.bsf.services.accountsservice.facades.AccountFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String ACCOUNT_TEMPLATE = "/api/accounts/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AccountFacade accountFacade;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Send get Account request with number not found.
     */
    @Test
    @DisplayName("Get Account - not found")
    public void testGetAccountWithNumberNotFound() throws Exception {
        lenient().when(accountFacade.retrieveAccountByNumber(any())).thenThrow(ServiceError.ACCOUNT_NOT_FOUND_ERROR.buildExcpetion());
        mockMvc.perform(get(ACCOUNT_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Send get Account request with code success.
     */
    @Test
    @DisplayName("Get Account- with account Number success")
    public void testGetAccountWithNumberSuccess() throws Exception {
        lenient().when(accountFacade.retrieveAccountByNumber(any())).thenReturn(buildAccountData());
        mockMvc.perform(get(ACCOUNT_TEMPLATE + "1188725442789754331")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    private AccountData buildAccountData() {
        return AccountData.builder()
                .number("1188725442789754331")
                .balance(1500.75)
                .bankName("ADCB")
                .ownerName("ABANOUB NASSER")
                .build();

    }


}
