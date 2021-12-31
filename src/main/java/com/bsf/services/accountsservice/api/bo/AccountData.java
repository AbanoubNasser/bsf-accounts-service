package com.bsf.services.accountsservice.api.bo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountData {

    private String number;

    private String ownerName;

    private String bankName;

    private double balance;
}
