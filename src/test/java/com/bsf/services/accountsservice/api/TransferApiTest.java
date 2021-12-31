package com.bsf.services.accountsservice.api;

import com.bsf.services.accountsservice.api.bo.TransferRequest;
import com.bsf.services.accountsservice.exception.ServiceError;
import com.bsf.services.accountsservice.facades.TransferFacade;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferApiTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String TRANSFER_TEMPLATE = "/api/transfers/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TransferFacade transferFacade;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Send process transfer request without Body.
     */
    @Test
    @DisplayName("Process transfer  - Without Body")
    public void testProcessTransferWithoutBody() throws Exception {
        mockMvc.perform(post(TRANSFER_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Send process transfer request without from account field.
     */
    @Test
    @DisplayName("Process transfer  - Without from Account")
    public void testProcessTransferWithoutFromAccount() throws Exception {
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequestWithoutFromAccountField()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Send process transfer request without to account field.
     */
    @Test
    @DisplayName("Process transfer  - Without to Account")
    public void testProcessTransferWithoutToAccount() throws Exception {
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequestWithoutToAccountField()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Send process transfer request without amount field.
     */
    @Test
    @DisplayName("Process transfer  - Without amount")
    public void testProcessTransferWithoutAmount() throws Exception {
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequestWithoutAmountField()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Send process transfer request with zero amount.
     */
    @Test
    @DisplayName("Process transfer  - With zero amount")
    public void testProcessTransferWitZeroAmount() throws Exception {
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequestWithZeroAmountField()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Send process transfer request success.
     */
    @Test
    @DisplayName("Process transfer  - success")
    public void testProcessTransfer_SuccessStatus() throws Exception {
        lenient().doNothing().when(transferFacade).processTransfer(any());
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequest()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Send process transfer request with not found Account.
     */
    @Test
    @DisplayName("Process transfer  - WithNotFoundAccount")
    public void testProcessTransfer_WithNotFoundAccount() throws Exception {
        lenient().doThrow(ServiceError.ACCOUNT_NOT_FOUND_ERROR.buildExcpetion()).when(transferFacade).processTransfer(any());
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequest()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Send process transfer request with not enough balance.
     */
    @Test
    @DisplayName("Process transfer  - WithNotEnoughBalance")
    public void testProcessTransfer_WithNotEnoughBalance() throws Exception {
        lenient().doThrow(ServiceError.NO_ENOUGH_BALANCE_ERROR.buildExcpetion()).when(transferFacade).processTransfer(any());
        mockMvc.perform(
                post(TRANSFER_TEMPLATE)
                        .content(mapper.writeValueAsString(buildTransferRequest()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private Object buildTransferRequest() {
        return TransferRequest.builder().amount(50.0)
                .fromAccount("1837351434526793264")
                .toAccount("183735143452679376").build();
    }

    private Object buildTransferRequestWithoutFromAccountField() {
        return TransferRequest.builder().amount(50.0).toAccount("183735143452679376").build();
    }

    private Object buildTransferRequestWithoutToAccountField() {
        return TransferRequest.builder().amount(50.0).fromAccount("183735143452679376").build();
    }

    private Object buildTransferRequestWithoutAmountField() {
        return TransferRequest.builder().fromAccount("183735143452679376").toAccount("1837351434526793264").build();
    }

    private Object buildTransferRequestWithZeroAmountField() {
        return TransferRequest.builder().amount(0.0).fromAccount("183735143452679376").toAccount("1837351434526793264").build();
    }


}
