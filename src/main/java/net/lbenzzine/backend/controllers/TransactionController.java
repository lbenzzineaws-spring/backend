package net.lbenzzine.backend.controllers;


import net.lbenzzine.backend.models.Transaction;
import net.lbenzzine.backend.repositories.CustomerRepository;
import net.lbenzzine.backend.repositories.TransactionRepository;
import net.lbenzzine.backend.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "http://localhost:3002")
public class TransactionController {

    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private RewardService rewardService;
    private Exception ResourceNotFoundException;

    @Autowired
    public TransactionController(RewardService rewardService, CustomerRepository customerRepository,
                                 TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.rewardService = rewardService;
    }

    @GetMapping("/customers/{id}/transactions")
    public List<Transaction> getTransactionsByCustomer(
            @PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {

        return customerRepository.findById(customerId).get().getTransactions();
    }

    @PostMapping("/customers/{id}/transactions")
    public Transaction createTransaction(@PathVariable(value = "id") Long customerId,
                                         @Validated @RequestBody Transaction transaction) throws ResourceNotFoundException {
        return rewardService.lookupCustomerById(customerId).map(customer -> {
            customer.getTransactions().add(transaction);
            rewardService.calculateRewards(customer);
            rewardService.calculateMonthlyRewards(customer, Month.SEPTEMBER);
            rewardService.totalRewardPoints(customer);
            return transactionRepository.save(transaction);
        }).orElseThrow(() -> new ResourceNotFoundException("customere not found"));
    }

    @PutMapping("/customers/{id}/transactions/{id}")
    public Transaction updateTransaction(@PathVariable(value = "id") Long customereId,
                                         @PathVariable(value = "id") Long tranId, @Validated @RequestBody Transaction trans)
            throws ResourceNotFoundException {
        if (!customerRepository.existsById(customereId)) {
            throw new ResourceNotFoundException("memberId not found");
        }

        return transactionRepository.findById(tranId).map(transaction -> {
            transaction.setAmount(trans.getAmount());
            rewardService.calculateRewards(transaction);
            return transactionRepository.save(transaction);
        }).orElseThrow(() -> new ResourceNotFoundException("transaction id not found"));
    }

}
