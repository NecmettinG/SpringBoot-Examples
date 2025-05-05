package com.example.stockmarket.config;

import com.example.stockmarket.entity.Asset;
import com.example.stockmarket.entity.Customer;
import com.example.stockmarket.entity.Employee;
import com.example.stockmarket.repository.AssetRepository;
import com.example.stockmarket.repository.CustomerRepository;
import com.example.stockmarket.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConfig{


    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                                EmployeeRepository employeeRepository,
                                                AssetRepository assetRepository){

        return args -> {

            Employee abdul = new Employee(
                    "abdul",
                    "samed",
                    "abdul@gmail.com"

            );

            Employee josh = new Employee(
                    "josh",
                    "bush",
                    "josh@gmail.com"
            );

            employeeRepository.saveAll(List.of(abdul, josh));

            Customer neco = new Customer(
                    "neco",
                    "gedikli",
                    "gedikligaming61@gmail.com"

            );

            Customer quandale = new Customer(
                    "quandale",
                    "dingle",
                    "quandale@gmail.com"
            );

            neco.setEmployee(employeeRepository.findEmployeeByFirstname("abdul"));
            quandale.setEmployee(employeeRepository.findEmployeeByFirstname("josh"));
            customerRepository.saveAll(List.of(neco, quandale));

            Asset asset1 = new Asset(

                    "TRY",
                    50000.0,
                    50000.0
            );

            Asset asset2 = new Asset(

                    "GOLD",
                    50.0,
                    50.0
            );

            Asset asset3 = new Asset(

                    "TRY",
                    1000.0,
                    1000.0
            );

            Asset asset4 = new Asset(

                    "SILVER",
                    100.0,
                    100.0
            );

            asset1.setCustomer(customerRepository.findCustomerByFirstName("neco"));
            asset2.setCustomer(customerRepository.findCustomerByFirstName("neco"));
            asset3.setCustomer(customerRepository.findCustomerByFirstName("quandale"));
            asset4.setCustomer(customerRepository.findCustomerByFirstName("quandale"));

            assetRepository.saveAll(List.of(asset1, asset2, asset3, asset4));
        };
    }

}
