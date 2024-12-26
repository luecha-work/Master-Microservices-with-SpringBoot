package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.sevice_contract.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private IAccountsService accountsService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccounts(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/accounts")
    public ResponseEntity<CustomerDto> getAccountDetail(@RequestParam String mobileNumber) {
        System.out.println("AccountsController mobileNumber: " + mobileNumber);
        CustomerDto customerDto = accountsService.getAccountDetailByMobilePhone(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
}
