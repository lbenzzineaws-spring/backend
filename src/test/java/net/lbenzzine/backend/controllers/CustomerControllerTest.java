package net.lbenzzine.backend.controllers;

import net.lbenzzine.backend.models.Customer;
import net.lbenzzine.backend.services.RewardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Mock
    private Customer customerMock;

    @MockBean
    private RewardService rewardService;

    @MockBean
    private CustomerController customerController;

    @BeforeAll
    public void setup() {
        when(customerMock.getId()).thenReturn(1L);
        when(customerMock.getFirstname()).thenReturn("Test_Fname");
        when(customerMock.getLastname()).thenReturn("Test_Lname");
        when(customerMock.getEmail()).thenReturn("test_fname.test_lname@email.com");
    }

    @Test
    void getOneCustomer() throws ResourceNotFoundException {
        when(rewardService.lookupCustomerById(1L)).thenReturn(Optional.of(customerMock));
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customers/1", Customer.class);

        assertThat(response.getStatusCode().equals(200));
    }

    @Test
    void getCustomers() throws Exception, ResourceNotFoundException {
        customerMock.setFirstname("Test");
        customerMock.setLastname("Testlastname");
        List<Customer> allCustomers = singletonList(customerMock);
        when(rewardService.lookupAll()).thenReturn(allCustomers);
        ResponseEntity<String> response = restTemplate.getForEntity("/customers", String.class);

        assertThat(response.getStatusCode().equals(200));
        assertThat(response.getBody().contains("Test"));
        assertThat(response.getBody().contains("Testlastname"));
    }


}
