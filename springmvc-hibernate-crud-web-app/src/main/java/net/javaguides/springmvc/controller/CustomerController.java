package net.javaguides.springmvc.controller;

import java.io.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springmvc.entity.Customer;
import net.javaguides.springmvc.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    private static int id =1;
    private ReentrantLock lock = new ReentrantLock(true);

    public void outputFile(int total){
        lock.lock();
        if (id<total) {
            try {
                FileWriter fw = new FileWriter("../file.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                List<Customer> customerList = customerService.getCustomersById(id);
                for (int i = 0; i < customerList.size(); i++) {
                    String message = customerList.get(i).toString();
                    bw.write(message);
                    bw.flush();
                }
                id += 20;
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    @GetMapping("/list")
    public String listCustomers(Model theModel) {
        List < Customer > theCustomers = customerService.getCustomers();
        theModel.addAttribute("customers", theCustomers);
        return "list-customers";
    }

    @GetMapping("/showForm")
    public String showFormForAdd(Model theModel) {
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
        customerService.saveCustomer(theCustomer);
        return "redirect:/customer/list";
    }

    @GetMapping("/updateForm")
    public String showFormForUpdate(@RequestParam("customerId") int theId,
        Model theModel) {
        Customer theCustomer = customerService.getCustomer(theId);
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int theId) {
        customerService.deleteCustomer(theId);
        return "redirect:/customer/list";
    }

    @GetMapping("/savetoFile")
    public void savetoFile(){
        int total = customerService.getTotalNumber();

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                outputFile(total);
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                outputFile(total);
            }
        });

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                outputFile(total);
            }
        });

        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                outputFile(total);
            }
        });

        Thread e = new Thread(new Runnable() {
            @Override
            public void run() {
                outputFile(total);
            }
        });

        while(id<total) {
            a.run();
            b.run();
            c.run();
            d.run();
            e.run();
        }
    }

    }
}