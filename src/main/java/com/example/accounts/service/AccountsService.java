package com.example.accounts.service;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.sevice_contract.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private AccountsMapper accountsMapper;


    @Override
    public void createAccounts(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToCustomer(customerDto);


        System.out.println("AccountsService map customerDto: " + customerDto.getEmail());
        System.out.println("AccountsService map customer: " + customer.getEmail());

        customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }

        customer.setCreatedBy("System");
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

        System.out.println("AccountsService savedCustomer: " + savedCustomer.getCreatedBy());
    }

    @Override
    public CustomerDto getAccountDetailByMobilePhone(String phoneNumber) {
        Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", phoneNumber)
        );
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );


        CustomerDto customerDto = customerMapper.mapToCustomerDto(customer);
        AccountsDto accountsDto = accountsMapper.mapToAccountsDto(account);
        customerDto.setAccountsDto(accountsDto);

        return customerDto;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        long randomAccountNumber = 100000000L + (long) (Math.random() * 900000000L);

        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedBy("System");
        newAccount.setCreatedAt(LocalDateTime.now());

        return newAccount;
    }
}
