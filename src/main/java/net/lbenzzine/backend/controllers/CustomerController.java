package net.lbenzzine.backend.controllers;


import net.lbenzzine.backend.dto.MonthlyRewards;
import net.lbenzzine.backend.dto.TotalRewards;
import net.lbenzzine.backend.models.Customer;
import net.lbenzzine.backend.services.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "http://localhost:3002")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private Exception ResourceNotFoundException;
    private RewardService rewardService;

    @Autowired
    public CustomerController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        LOGGER.info("a number of " + rewardService.lookupAll().size() + " existing customres has been");
        return this.rewardService.lookupAll();
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {
        Customer customer = rewardService.lookupCustomerById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@Validated @RequestBody Customer customer) {
        return rewardService.createOrUpdateCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable(value = "id") Long customerId,
            @Validated @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = rewardService.lookupCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found :: " + customerId));

        customer.setFirstname(customerDetails.getFirstname());
        customer.setLastname(customerDetails.getLastname());
        customer.setEmail(customerDetails.getEmail());
        final Customer updatedCustomer = rewardService.createOrUpdateCustomer(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteCustomer(
            @PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {
        Customer customer = rewardService.lookupCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found :: " + customerId));

        rewardService.deleteCustomer(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    //Endpoints for rewards points

    @GetMapping("/customers/{id}/{month}")
    @ResponseBody
    public ResponseEntity<MonthlyRewards> getMonthlyRewards(@PathVariable(value = "id") Long customerId, @PathVariable(value = "month") Month month)
            throws ResourceNotFoundException {

        Customer customer = rewardService.lookupCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found :: " + customerId));

        MonthlyRewards paylod = new MonthlyRewards();
        paylod.setCustomerId(customerId);
        paylod.setMonth(month);
        paylod.setMonthlyReward(this.rewardService.calculateMonthlyRewards(customer, month));
        return ResponseEntity.ok().body(paylod);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<MonthlyRewards[]> getTrimesterRewards(@PathVariable(value = "id") Long customerId, @RequestParam Month[] trimester)
            throws ResourceNotFoundException {

        Customer customer = rewardService.lookupCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found :: " + customerId));

        MonthlyRewards[] payloads = new MonthlyRewards[3];
        for (int i = 0; i < payloads.length; i++) {
            payloads[i] = new MonthlyRewards();
            payloads[i].setCustomerId(customerId);
            payloads[i].setMonth(trimester[i]);
            payloads[i].setMonthlyReward(this.rewardService.calculateMonthlyRewards(customer, trimester[i]));
        }
        return ResponseEntity.ok().body(payloads);
    }

    @GetMapping("/customers/{id}/total")
    public ResponseEntity<TotalRewards> getTotalyRewards(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {

        Customer customer = rewardService.lookupCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found :: " + customerId));

        TotalRewards paylod = new TotalRewards();
        paylod.setCustomerId(customerId);
        paylod.setTotalRewards(this.rewardService.totalRewardPoints(customer));
        return ResponseEntity.ok().body(paylod);
    }
}



