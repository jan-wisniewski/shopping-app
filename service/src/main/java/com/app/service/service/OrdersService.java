package com.app.service.service;

import com.app.service.exceptions.ServiceException;
import org.eclipse.collections.impl.collector.Collectors2;
import persistence.converters.impl.OrdersJsonConverter;
import persistence.enums.Category;
import persistence.models.Customer;
import persistence.models.Order;
import persistence.models.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrdersService {

    private List<Order> orders;
    private static final String DISCOUNT_TWO_DAYS_TO_ORDER = "0.02";
    private static final String DISCOUNT_CUSTOMER_UNDER_OR_EQUAL_25 = "0.03";

    public OrdersService(String jsonFilename) {
        this.orders = init(jsonFilename);
    }

    private List<Order> init(String jsonFilename) {
        return new OrdersJsonConverter(jsonFilename)
                .fromJson()
                .orElseThrow();
    }

    public List<Order> showAllOrders() {
        return orders;
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

    public Category categoryWhichProductsBuyTheMost() {
        return orders.stream()
                .flatMap(order -> Collections.nCopies(order.getQuantity(), order.getProduct().getCategory()).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new ServiceException("FAILED"))
                .getKey();
    }

    public Map<Month, Integer> monthAndOrderedProducts() {
        return orders.stream()
                .collect(Collectors.groupingBy(o -> o.getOrderDate().getMonth(),
                        Collectors.summingInt(Order::getQuantity)));

    }

    public Map<Month, Category> mostPopularCategoryInMonth() {
        return orders.stream()
                .collect(Collectors.groupingBy(o -> o.getOrderDate().getMonth()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .stream()
                        .collect(Collectors.groupingBy(o ->
                                o.getProduct().getCategory(), Collectors.counting()))
                        .entrySet().stream()
                        .max(Comparator.comparing(Map.Entry::getKey))
                        .orElseThrow()
                        .getKey()
                ));
    }

    public List<Customer> findCustomersByNumberOfOrders(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Incorrect number of orders");
        }
        return orders.stream()
                .filter(o -> o.getQuantity() >= quantity)
                .map(Order::getCustomer)
                .collect(Collectors.toList());
    }

    public BigDecimal averagePriceInTimeRange(LocalDate from, LocalDate to) {
        return orders.stream()
                .filter(o -> o.getOrderDate().isAfter(from) && o.getOrderDate().isBefore(to))
                .map(o -> (o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity()))))
                .collect(Collectors2.summarizingBigDecimal(o -> o))
                .getAverage();
    }

    public BigDecimal countTotalPriceWithDiscounts() {
        return orders
                .stream()
                .map(o -> o
                        .getProduct()
                        .getPrice()
                        .multiply(BigDecimal.ONE.subtract(calculateDiscount(o))
                                .multiply(BigDecimal.valueOf(o.getQuantity()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateDiscount(Order order) {
        BigDecimal discount1 = BigDecimal.ZERO;
        BigDecimal discount2 = BigDecimal.ZERO;
        if (isAtLeastTwoDaysToOrder(order)) {
            discount1 = new BigDecimal(DISCOUNT_TWO_DAYS_TO_ORDER);
        }
        if (isCustomerUnderOrEqual25Years(order)) {
            discount2 = new BigDecimal(DISCOUNT_CUSTOMER_UNDER_OR_EQUAL_25);
        }
        return discount2.max(discount1);
    }

    private boolean isAtLeastTwoDaysToOrder(Order order) {
        if (order == null) {
            throw new ServiceException("Order is null");
        }
        return ChronoUnit.DAYS.between(order.getOrderDate(), LocalDate.now()) >= 2;
    }

    private boolean isCustomerUnderOrEqual25Years(Order order) {
        if (order == null) {
            throw new ServiceException("Order is null");
        }
        return order.getCustomer().getAge() <= 25;
    }
}