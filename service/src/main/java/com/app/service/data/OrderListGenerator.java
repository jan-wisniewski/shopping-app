package com.app.service.data;

import com.app.service.data.generic.Generator;
import persistence.models.Customer;
import persistence.models.Order;
import persistence.models.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderListGenerator implements Generator<List<Order>> {

    private List<Customer> customers;
    private List<Product> products;
    private final Random random = new Random();

    public OrderListGenerator(List<Customer> customers, List<Product> products) {
        this.customers = customers;
        this.products = products;
    }

    @Override
    public List<Order> generate(int size) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orders.add(Order.builder()
                    .customer(customers.get(random.nextInt(customers.size())))
                    .orderDate(LocalDate.now().plusDays(random.nextInt(360)))
                    .product(products.get(random.nextInt(products.size())))
                    .quantity(random.nextInt(5)+1)
                    .build());
        }
        return orders;
    }
}
