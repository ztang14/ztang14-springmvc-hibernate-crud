package net.javaguides.springmvc.thread;

import net.javaguides.springmvc.entity.Customer;
import net.javaguides.springmvc.service.CustomerService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;


public class SaveToFileThread implements Runnable {


    public static ReentrantLock WRITE_FILE_LOCK = new ReentrantLock(true);

    private File file;


    private int startId;


    private CustomerService customerService;

    public SaveToFileThread(File file, int startId, CustomerService customerService) {
        this.file = file;
        this.startId = startId;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        int totalPage = 5;
        int size = 20;
        for (int i = 0; i < totalPage; i++) {
            List<Customer> customers = customerService.pageQueryCustomers(startId, (i + 1), size);
            if (Objects.isNull(customers) || customers.isEmpty()) {
                break;
            }
            WRITE_FILE_LOCK.lock();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                for (Customer customer : customers) {
                    writer.write(customer.toString());
                    writer.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                WRITE_FILE_LOCK.unlock();
            }
        }
    }
}
