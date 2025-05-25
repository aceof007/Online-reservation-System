package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.Customer;
import com.ORS.Online_reservation_System.repositories.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

}
