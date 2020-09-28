package net.lbenzzine.backend.services;

import net.lbenzzine.backend.models.Customer;
import net.lbenzzine.backend.models.Transaction;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface RewardService {

    Optional<Customer> lookupCustomerById(Long customerId);

    List<Customer> lookupAll();

    Customer createOrUpdateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    void addTransactions();

    Long calculateRewards(Transaction transaction);

    Long calculateRewards(Customer customer);

    Long calculateMonthlyRewards(Customer customer, Month month);

    Long totalRewardPoints(Customer customer);

}
