package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Order;
import com.example.stockmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "customer/{customerId}/order")
public class OrderCustomerController {

    private final OrderService orderService;

    @Autowired
    public OrderCustomerController(OrderService orderService){

        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@PathVariable("customerId") Long customerId){

        return orderService.getOrders(customerId);
    }

    @PostMapping
    public void addOrder(@RequestBody Order order){


        orderService.createOrder(order);
    }

    @DeleteMapping(path = "{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId){

        orderService.deleteOrder(orderId);
    }
}
