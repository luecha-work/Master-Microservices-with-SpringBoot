package com.example.accounts.sevice_contract;

import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Creates a new account with the provided account details.
     *
     * @param customerDto the account details to create the new account
     */
    void createAccounts(CustomerDto customerDto);
}
