package com.ORS.Online_reservation_System.Controllers;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import com.ORS.Online_reservation_System.model.Customer;
import com.ORS.Online_reservation_System.serviceimplementation.CustomerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("siteForm") LoginDTO formData, BindingResult result, RedirectAttributes redirectAttrs) {
        Customer customer = new Customer();
        Date date = new Date();

        customer.setEmail(formData.getEmail());
        customer.setPassword(formData.getPassword());
        customer.setFirstName(formData.getFirstName());
        customer.setLastName(formData.getLastName());
        customer.setUsername(formData.getUsername());
        customer.setPhoneNumber(formData.getCountryCode() + formData.getPhoneNumber());
        customer.setRegistraionDate(date);
        customer.setRole("CUSTOMER");
        customer.setStatus("ACTIVE");

        customerService.addCustomer(customer);
        System.out.println("Customer added");
        return "redirect:/login";
    }
}
