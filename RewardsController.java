package com.rewards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.model.Rewards;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.IRewardsService;

@RestController
@RequestMapping("/customers")
public class RewardsController {

    @Autowired
    IRewardsService rewardsService;

    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping(value = "rewards/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId){
    	
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer == null)
        {
        	new ResponseEntity<>("Invalid / Missing customer Id ",HttpStatus.NOT_FOUND);
        }
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards,HttpStatus.OK);
    }
    
    @GetMapping(value = "/transactions/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable("customerId") Long customerId){
    	
    	List<Transaction> transactions = transactionRepository.findAllByCustomerId(customerId);
        if(transactions.isEmpty())
        {
        	new ResponseEntity<>("No Transactions",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }

}
