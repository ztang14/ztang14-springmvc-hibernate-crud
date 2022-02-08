package net.javaguides.springmvc.service;

import net.javaguides.springmvc.dao.CustomerDAO;
import net.javaguides.springmvc.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {
        customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int theId) {
        return customerDAO.getCustomer(theId);
    }

    @Override
    @Transactional
    public void deleteCustomer(int theId) {
        customerDAO.deleteCustomer(theId);
    }

    @Override
    public List<Customer> getCustomersById(int id) {
        return customerDAO.getCustomersById(id);
    }

    @Override
    public int getTotalNumber() {
        return customerDAO.getTotalNumber();
    }


    @Override
    public List<Customer> pageQueryCustomers(int startId, int page, int size) {
        return customerDAO.pageCustomers(startId, page, size);
    }

}