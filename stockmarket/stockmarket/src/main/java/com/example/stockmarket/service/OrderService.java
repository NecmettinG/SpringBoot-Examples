package com.example.stockmarket.service;

import com.example.stockmarket.entity.*;
import com.example.stockmarket.repository.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService{

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;
    private final AssetSpecialRepository assetSpecialRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, AssetRepository assetRepository,
                        AssetSpecialRepository assetSpecialRepository){

        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
        this.assetSpecialRepository = assetSpecialRepository;
    }


    public List<Order> getOrders(Long customerId){

        return orderRepository.findOrderByCustomerId(customerId);
    }

    @Transactional
    public void createOrder(Order order){

        order.setStatus(Status.PENDING);
        orderRepository.save(order);
        double cost = order.getPrice() * order.getSize();
        Asset assetRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId(order.getAssetName(), order.getCustomer().getId());

        Asset tryRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId("TRY", order.getCustomer().getId());

        if(tryRecord.getSize() >= cost){

            assetRecord.setUsableSize(assetRecord.getUsableSize() * (0.5));

            tryRecord.setSize(tryRecord.getSize() - cost); //Advance payment in TRY is done.
            tryRecord.setUsableSize(tryRecord.getSize());

        }

        else{

            throw new IllegalStateException("insufficient funds. Please load more to your TRY asset.");
        }
    }

    @Transactional
    public void approveOrder(Long orderId, Status status){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("There is no such order to approve."));

        order.setStatus(status);

        Asset assetRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId(order.getAssetName(),
                order.getCustomer().getId());

        Asset tryRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId("TRY", order.getCustomer().getId());
        double cost = order.getPrice() * order.getSize();

        if(status == Status.MATCHED){

            if(order.getSide() == Side.BUY){

                assetRecord.setSize(assetRecord.getSize() + order.getSize());
                assetRecord.setUsableSize(assetRecord.getSize());
            }

            else if(order.getSide() == Side.SELL){

                if(assetRecord.getSize()>=order.getSize()){

                    assetRecord.setSize(assetRecord.getSize()-order.getSize());
                    assetRecord.setUsableSize(assetRecord.getUsableSize());
                    tryRecord.setSize(tryRecord.getSize() + cost);
                    tryRecord.setUsableSize(tryRecord.getSize());
                }
            }
        }

        else if(status == Status.CANCELED){

            assetRecord.setUsableSize(assetRecord.getUsableSize() * (2.0));

            tryRecord.setSize(tryRecord.getSize() + cost); //refunding advance TRY payment.
            tryRecord.setUsableSize(tryRecord.getSize());

        }

        else{

            throw new IllegalStateException("Please try to enter Status MATCHED or CANCELED.");
        }

    }

    @Transactional
    public void deleteOrder(Long orderId){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("There is no such order to delete."));

        Asset tryRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId("TRY", order.getCustomer().getId());

        Asset assetRecord = assetSpecialRepository.findAssetByAssetNameAndCustomerId(order.getAssetName(),
                order.getCustomer().getId());

        double cost = order.getPrice() * order.getSize();

        if(order.getStatus() == Status.PENDING) {

            assetRecord.setUsableSize(assetRecord.getUsableSize() * (2.0));
            tryRecord.setSize(tryRecord.getSize() + cost); //refunding advance TRY payment.
            tryRecord.setUsableSize(tryRecord.getSize());
            orderRepository.delete(order);

        }

        else{

            throw new IllegalStateException("The order you entered is not a PENDING order. It is "+ order.getStatus());
        }

    }

}
