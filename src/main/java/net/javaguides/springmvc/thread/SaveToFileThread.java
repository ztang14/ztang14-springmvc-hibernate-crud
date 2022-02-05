package net.javaguides.springmvc.thread;

import net.javaguides.springmvc.entity.Customer;
import net.javaguides.springmvc.service.CustomerService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据库数据保存到文件线程类
 */
public class SaveToFileThread implements Runnable {

    /**
     * 写文件的锁
     */
    public static ReentrantLock WRITE_FILE_LOCK = new ReentrantLock(true);

    /**
     * 写入的目标文件
     */
    private File file;

    /**
     * 每个线程处理哪一页
     */
    private int startId;

    /**
     * service类
     */
    private CustomerService customerService;

    public SaveToFileThread(File file, int startId, CustomerService customerService) {
        this.file = file;
        this.startId = startId;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        //线程每页查询20条数据，总共查询5页
        int totalPage = 5;
        int size = 20;
        for (int i = 0; i < totalPage; i++) {
            //从数据库查询数据
            List<Customer> customers = customerService.pageQueryCustomers(startId, (i + 1), size);
            if (Objects.isNull(customers) || customers.isEmpty()) {
                //没有数据了，直接跳出
                break;
            }
            //获取文件锁，写入文件
            WRITE_FILE_LOCK.lock();
            //获取文件写入writer，要用追加的方式写入
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                //遍历数据库的查询结果
                for (Customer customer : customers) {
                    //每个对象数据写成一行文件数据
                    writer.write(customer.toString());
                    writer.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //最后关闭文件写入锁
                WRITE_FILE_LOCK.unlock();
            }
        }
    }
}
