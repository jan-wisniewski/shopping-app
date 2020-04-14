package com.app.service.service;

import com.app.service.exceptions.ServiceException;
import persistence.converters.impl.OrdersJsonConverter;
import persistence.enums.Category;
import persistence.models.Customer;
import persistence.models.Order;
import persistence.models.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersService {

    private List<Order> orders;

    public OrdersService(String jsonFilename) {
        this.orders = init(jsonFilename);
    }

    private List<Order> init(String jsonFilename) {
        return new OrdersJsonConverter(jsonFilename)
                .fromJson()
                .orElseThrow();
    }

    public String showAllOrders() {
        return orders.stream()
                .map(o -> o.getCustomer() + " bought " + o.getProduct() +
                        " (quantity: " + o.getQuantity() + "), date: " + o.getOrderDate())
                .collect(Collectors.joining("\n"));
    }

    public Map<Category, Product> findMostExpensiveProductInCategory() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getProduct().getCategory(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(o -> o.getProduct().getPrice())),
                                resultOrderOp -> resultOrderOp
                                        .orElseThrow(() -> new ServiceException("Failed!"))
                                        .getProduct()
                        )
                ));
    }

    public List<Product> groupProductByCustomer(String email) {
        if (email == null) {
            throw new ServiceException("Email is null");
        }
        return orders.stream()
                .filter(o -> o.getCustomer().getEmail().equals(email))
                .map(Order::getProduct)
                .collect(Collectors.toList());
    }

    public LocalDate dateWithMostOrders() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate))
                .entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue().size()))
                .orElseThrow(IllegalArgumentException::new)
                .getKey();
    }

    public LocalDate dateWithLeastOrders() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate))
                .entrySet()
                .stream()
                .min(Comparator.comparing(e -> e.getValue().size()))
                .orElseThrow(IllegalArgumentException::new)
                .getKey();
    }

    public Customer spentTheMost() {
        return orders.stream()
                .collect(
                        Collectors.groupingBy(
                                Order::getCustomer,
                                Collectors.mapping(o -> o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())), Collectors.toList())
                        ))
                .entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue().stream().reduce(BigDecimal.ZERO, BigDecimal::add)))
                .orElseThrow(() -> new IllegalStateException("xxxx"))
                .getKey();
    }

}
