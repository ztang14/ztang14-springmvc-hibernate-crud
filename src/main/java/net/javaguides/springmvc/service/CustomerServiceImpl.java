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

    /**
     * 分页查询数据
     *
     * @param startId 起始数据ID参数
     * @param page    查询页码，从1开始
     * @param size    每页查询数量
     * @return 查询的数据列表
     */
    @Override
    public List<Customer> pageQueryCustomers(int startId, int page, int size) {
        return customerDAO.pageCustomers(startId, page, size);
    }

}