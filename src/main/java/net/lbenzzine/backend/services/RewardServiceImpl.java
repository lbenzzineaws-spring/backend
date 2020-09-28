package net.lbenzzine.backend.services;


import net.lbenzzine.backend.models.Customer;
import net.lbenzzine.backend.models.Transaction;
import net.lbenzzine.backend.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RewardServiceImpl implements RewardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardServiceImpl.class);

    private CustomerRepository customerRepository;


    private RestTemplate restTemplate = new RestTemplate();

    public RewardServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    @Autowired
    public RewardServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }


    @Transactional
    public void addTransactions() {

        // Create membres with transactions to initialize H2 db
        Customer customer1 = new Customer("Mike", "Smith", "msmith@mail.com", 0L, 0l);
        //Creating transactions for customer1
        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(new Transaction("09-01-2020", 185L, 0l));
        transactions1.add(new Transaction("09-10-2020", 120L, 0l));
        transactions1.add(new Transaction("09-15-2020", 130L, 0l));
        transactions1.add(new Transaction("08-01-2020", 185L, 0l));
        transactions1.add(new Transaction("08-10-2020", 120L, 0l));
        transactions1.add(new Transaction("08-15-2020", 130L, 0l));
        customer1.setTransactions(transactions1);

        //Calculating rewards points for customer1 with all the transactions
        this.calculateRewards(customer1);
        this.calculateMonthlyRewards(customer1, Month.SEPTEMBER);// this the current Month when this was coded
        this.totalRewardPoints(customer1);
        customerRepository.save(customer1);

        Customer customer2 = new Customer("Steve", "Smith", "ssmith@mail.com", 0l, 0L);
        //Creating transactions for customer2
        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(new Transaction("09-21-2020", 175L, 0l));
        transactions2.add(new Transaction("09-20-2020", 145L, 0l));
        transactions2.add(new Transaction("09-25-2020", 130L, 0l));
        transactions2.add(new Transaction("07-01-2020", 185L, 0l));
        transactions2.add(new Transaction("07-10-2020", 120L, 0l));
        transactions2.add(new Transaction("07-15-2020", 130L, 0l));
        customer2.setTransactions(transactions2);

        //Calculating rewards points for customer2 with all the transactions
        this.calculateRewards(customer2);
        this.calculateMonthlyRewards(customer2, Month.SEPTEMBER);// this the current Month when this was coded
        this.totalRewardPoints(customer2);
        customerRepository.save(customer2);

    }

    @Override
    public Long calculateRewards(Transaction transaction) {
        Long earnedPoints = 0L;

        if (transaction.getAmount() <= 50) {
            transaction.setRewardPoints(0L);
            earnedPoints = 0L;
        } else if (50 < transaction.getAmount() && transaction.getAmount() <= 99) {
            earnedPoints = transaction.getAmount() - 50;
        } else if (transaction.getAmount() >= 100) {
            earnedPoints = (((transaction.getAmount() - 100) * 2) + 50);
        }
        transaction.setRewardPoints(earnedPoints);

        return earnedPoints;
    }


    public Optional<Customer> lookupCustomerById(Long id) {
        return customerRepository.findById(id);
    }


    public List<Customer> lookupAll() {
        LOGGER.info("Lookup all existing customers");
        return customerRepository.findAll();
    }

    public Customer createOrUpdateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    //Calaculate rewards points earneed per transaction per the reeequirements below
    /*A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
    (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
    Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.*/

    public Long calculateRewards(Customer customer) {
        Long earnedPoints = 0L;
        for (Transaction t : customer.getTransactions()) {
            if (t.getAmount() <= 50) {
                t.setRewardPoints(0L);
                earnedPoints = 0L;
            } else if (50 < t.getAmount() && t.getAmount() <= 99) {
                earnedPoints = t.getAmount() - 50;
            } else if (t.getAmount() >= 100) {
                earnedPoints = (((t.getAmount() - 100) * 2) + 50);
            }
            t.setRewardPoints(earnedPoints);
        }// end for loop

        return earnedPoints;
    }


    //Assuming date ar entered in the "mm-dd-yyyy" format
    public Long calculateMonthlyRewards(Customer customer, Month month) {
        Long monthlyPoints = 0L;
        for (Transaction t : customer.getTransactions()) {
            if (t.getTransactionDate().substring(0, 2).contains((String.valueOf(month.getValue())))) {
                monthlyPoints += t.getRewardPoints();
            }
        }

        customer.setMonthlyRewards(monthlyPoints);
        return monthlyPoints;
    }


    public Long totalRewardPoints(Customer customer) {
        Long totalQuaterlyPoints = 0L;
        for (Transaction t : customer.getTransactions()) {
            totalQuaterlyPoints += t.getRewardPoints();
        }
        customer.setQuarterlyRewards(totalQuaterlyPoints);
        return totalQuaterlyPoints;
    }


}
