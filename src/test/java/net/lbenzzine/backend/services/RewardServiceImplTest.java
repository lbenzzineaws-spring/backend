package net.lbenzzine.backend.services;


import net.lbenzzine.backend.models.Customer;
import net.lbenzzine.backend.models.Transaction;
import net.lbenzzine.backend.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardServiceImplTest {

    private static final Long CUSTOMER_ID = 1L;
    @InjectMocks
    RewardServiceImpl rewardService;
    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private Customer customerMock;

    @BeforeEach
    public void setup() {

       /* List<Transaction> singleTransaction = new ArrayList<>(1);
        Transaction single = new Transaction("09-01-2020", 200L,0l);*/

        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-01-2020", 185L, 0l);
        transactions1.add(t1);

        Transaction t2 = new Transaction("09-10-2020", 49L, 0l);
        transactions1.add(t2);

        Transaction t3 = new Transaction("09-15-2020", 99L, 0l);
        transactions1.add(t3);

        customerMock.setTransactions(transactions1);
    }


    @Test
    @DisplayName("😱" + " Test for find customer by customer Id")
    public void lookupCustomerByIdTest() {

        when(customerRepositoryMock.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerMock));

        assertNotNull(rewardService.lookupCustomerById(CUSTOMER_ID).get());

    }

    @Test
    @DisplayName("😱" + "Test for lookup all existing customers")
    public void lookupAllCustomeersTest() {
        when(customerRepositoryMock.findAll()).thenReturn(Arrays.asList(customerMock));

        assertTrue((rewardService.lookupAll().size() > 0));

    }

    @Test
    @DisplayName("😱" + " Test for create customer")
    public void saveCustomerTest() {

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        customerMock = new Customer("Test", "Test", "test@email.net", 100L, 100L);
        rewardService.createOrUpdateCustomer(customerMock);
        verify(customerRepositoryMock).save(customerCaptor.capture());

        assertTrue(customerCaptor.getValue().getMonthlyRewards() == 100L);
        assertTrue(customerCaptor.getValue().getFirstname() == "Test");

    }

    @Test
    @DisplayName("😱" + " Tets for Delete Customer")
    public void deleteCustomerTest() {
        rewardService.deleteCustomer(customerMock);

        verify(customerRepositoryMock).delete(any(Customer.class));
    }


    @Test
    @DisplayName("😱" + "Test for balance less that $50")
    void calculateRewardsTest() {
        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-01-2020", 49L, 0L);
        transactions1.add(t1);
        customerMock = new Customer("Test", "Test", "test@email.net", 0L, 0L);
        rewardService.createOrUpdateCustomer(customerMock);
        customerMock.setTransactions(transactions1);


        assertTrue(rewardService.calculateRewards(customerMock) == 0L);

    }

    @Test
    @DisplayName("😱" + " Test for balance less than $100 and more than $50")
    void calculateRewardsTest2() {
        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-01-2020", 99L, 0L);
        transactions1.add(t1);
        customerMock = new Customer("Test", "Test", "test@email.net", 0L, 0L);
        rewardService.createOrUpdateCustomer(customerMock);
        customerMock.setTransactions(transactions1);


        assertTrue(rewardService.calculateRewards(customerMock) == 49L);

    }

    @Test
    @DisplayName("😱" + " Test for balance more than $100 and more than $100")
    void calculateRewardsTest3() {
        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-07-2020", 200L, 0L);
        transactions1.add(t1);
        customerMock = new Customer("Test", "Test", "test@email.net", 0L, 0L);
        rewardService.createOrUpdateCustomer(customerMock);
        customerMock.setTransactions(transactions1);


        assertTrue(rewardService.calculateRewards(customerMock) == 250L);

    }


    @Test
    @DisplayName("😱" + " Test for Monthly rewards point calculation")
    void calculateMonthlyRewards() {
        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-01-2020", 200L, 0L);
        transactions1.add(t1);
        Transaction t2 = new Transaction("09-10-2020", 250L, 0L);
        transactions1.add(t2);
        Transaction t3 = new Transaction("09-20-2020", 500L, 0L);
        transactions1.add(t3);
        customerMock = new Customer("Test", "Test", "test@email.net", 0L, 0L);
        rewardService.createOrUpdateCustomer(customerMock);
        customerMock.setTransactions(transactions1);
        rewardService.calculateRewards(customerMock);

        //Total amount of points for the month of Spetember should be ### 250 + 350 + 850 ###
        assertTrue(rewardService.calculateMonthlyRewards(customerMock, Month.SEPTEMBER) == 1450L);
    }

    @Test
    @DisplayName("😱" + " Test for total rewards point calculation")
    void calculatetotalRewards() {
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        List<Transaction> transactions1 = new ArrayList<>();
        Transaction t1 = new Transaction("09-01-2020", 200L, 0L);
        transactions1.add(t1);
        Transaction t2 = new Transaction("09-10-2020", 250L, 0L);
        transactions1.add(t2);
        Transaction t3 = new Transaction("09-20-2020", 500L, 0L);
        transactions1.add(t3);

        //transactions for mont of Aug
        Transaction t4 = new Transaction("08-01-2020", 200L, 0L);
        transactions1.add(t4);
        Transaction t5 = new Transaction("08-10-2020", 350L, 0L);
        transactions1.add(t5);
        Transaction t6 = new Transaction("08-20-2020", 450L, 0L);
        transactions1.add(t6);

        //transactions for month of July
        Transaction t7 = new Transaction("07-01-2020", 200L, 0L);
        transactions1.add(t7);
        Transaction t8 = new Transaction("07-10-2020", 250L, 0L);
        transactions1.add(t8);
        Transaction t9 = new Transaction("07-20-2020", 167L, 0L);
        transactions1.add(t9);
        customerMock = new Customer("Test", "Test", "test@email.net", 0L, 0L);
        rewardService.createOrUpdateCustomer(customerMock);
        customerMock.setTransactions(transactions1);
        rewardService.calculateRewards(customerMock);

        assertTrue(rewardService.calculateMonthlyRewards(customerMock, Month.AUGUST) == 1550L);
        assertTrue(rewardService.calculateMonthlyRewards(customerMock, Month.JULY) == 784L);

        //Total amount of points for the month of Spetember should be ### (250 + 350 + 850) * 3 ###
        assertTrue(rewardService.totalRewardPoints(customerMock) == 3784L);
    }

}
