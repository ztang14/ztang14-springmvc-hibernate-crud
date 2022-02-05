package net.javaguides.springmvc.controller;

import net.javaguides.springmvc.entity.Customer;
import net.javaguides.springmvc.service.CustomerService;
import net.javaguides.springmvc.thread.SaveToFileThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel) {
        List<Customer> theCustomers = customerService.getCustomers();
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
    @ResponseBody
    public String savetoFile() {
        int threadSize = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadSize);
        File file = new File("customers.txt");
        if (file.exists()) {
            file.delete();
        }
        for (int i = 0; i < threadSize; i++) {
            int startId = i * 100 + 1;
            SaveToFileThread thread = new SaveToFileThread(file, startId, customerService);
            executor.submit(thread);
        }
        executor.shutdown();
        return "save to file success, file is " + file.getAbsolutePath();
    }

}