package com.example.accounts.sevice_contract;

import com.example.accounts.dto.AccountsDto;

public interface IAccountsService {

    /**
     * Creates a new account with the provided account details.
     *
     * @param accountsDto the account details to create the new account
     */
    void createAccounts(AccountsDto accountsDto);
}
