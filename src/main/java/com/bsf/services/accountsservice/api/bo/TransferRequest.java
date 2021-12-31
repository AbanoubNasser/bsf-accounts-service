package com.bsf.services.accountsservice.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class TransferRequest {

    @ApiModelProperty(value = "Transfer from Account number")
    @NotNull
    @NotBlank
    private String fromAccount;

    @ApiModelProperty(value = "Transfer to Account number")
    @NotNull
    @NotBlank
    private String toAccount;

    @ApiModelProperty(value = "Transfer amount")
    @NotNull
    @Min(1)
    private double amount;
}
