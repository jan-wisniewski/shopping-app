package com.app.service.service;

import com.app.service.exceptions.ServiceException;
import persistence.converters.impl.OrdersJsonConverter;
import persistence.enums.Category;
import persistence.models.Order;
import persistence.models.Product;

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

}
