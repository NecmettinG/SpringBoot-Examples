package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Order;
import com.example.stockmarket.entity.Status;
import com.example.stockmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "employee/order")
public class OrderEmployeeController{

    private final OrderService orderService;

    @Autowired
    public OrderEmployeeController(OrderService orderService){

        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@RequestParam(required = false) Long customerId){

        return orderService.getOrders(customerId);
    }

    @PutMapping(path = "{orderId}")
    public void approveOrder(@PathVariable("orderId") Long orderId, @RequestParam(required = true) Status status){

        orderService.approveOrder(orderId, status);
    }

    @DeleteMapping(path = "{orderId}")
    public void rejectOrder(@PathVariable("orderId") Long orderId){

        orderService.deleteOrder(orderId);
    }

}
