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

    @PostMapping("/account")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccounts(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/account")
    public ResponseEntity<CustomerDto> getAccountDetail(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsService.getAccountDetailByMobilePhone(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PatchMapping("/account")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        HttpStatus status = isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_500;
        String statusCode = isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_500;

        return ResponseEntity.status(status).body(new ResponseDto(statusCode, message));
    }

    @DeleteMapping("/account")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = isDeleted ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_500;
        String statusCode = isDeleted ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_500;

        return ResponseEntity.status(status).body(new ResponseDto(statusCode, message));
    }
}
