package net.javaguides.springmvc.dao;

import net.javaguides.springmvc.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> getCustomers();

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(int theId);

    public void deleteCustomer(int theId);

    void merge(int theId);

    List<Customer> getCustomersById(int id);

    int getTotalNumber();

    /**
     * 分页查询数据
     *
     * @param startId 起始数据ID参数
     * @param page    查询页码，从1开始
     * @param size    每页查询数量
     * @return 查询的数据列表
     */
    List<Customer> pageCustomers(int startId, int page, int size);

}
