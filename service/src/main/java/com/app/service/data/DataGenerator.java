package com.app.service.data;


import lombok.Getter;
import persistence.converters.impl.CustomerJsonConverter;
import persistence.converters.impl.OrdersJsonConverter;
import persistence.converters.impl.ProductsJsonConverter;
import persistence.models.Customer;
import persistence.models.Order;
import persistence.models.Product;

import java.util.List;

@Getter
public class DataGenerator {

    final static String ORDERS_FILENAME = "./resources/data/orders.json";
    final static String PRODUCTS_FILENAME = "./resources/data/products.json";
    final static String CUSTOMERS_FILENAME = "./resources/data/customers.json";

    public static List<Order> generateOrders(List<Customer> customers, List<Product> products, int size) {
        return new OrderListGenerator(customers, products).generate(size);
    }

    public static List<Customer> generateCustomers(int size) {
        return new CustomerListGenerator().generate(size);
    }

    public static List<Product> generateProducts(int size) {
        return new ProductsListGenerator().generate(size);
    }

    public static void saveToJsonFile(int numberOfCustomers, int numberOfProducts, int numberOfOrders) {
        List<Product> prods = generateProducts(numberOfProducts);
        List<Customer> customers = generateCustomers(numberOfCustomers);
        List<Order> orders = generateOrders(customers,prods,numberOfOrders);
        new OrdersJsonConverter(ORDERS_FILENAME).toJson(orders);
        new ProductsJsonConverter(PRODUCTS_FILENAME).toJson(prods);
        new CustomerJsonConverter(CUSTOMERS_FILENAME).toJson(customers);
    }

}
