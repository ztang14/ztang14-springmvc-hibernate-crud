package net.javaguides.springmvc.service;


import java.util.List;

import net.javaguides.springmvc.entity.Customer;

public interface CustomerService {

    public List < Customer > getCustomers();

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(int theId);

    public void deleteCustomer(int theId);

    List<Customer> getCustomersById(int id);

    int getTotalNumber();

}