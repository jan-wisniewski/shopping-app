package com.app.ui.menu;

import com.app.service.email.EmailService;
import com.app.service.service.OrdersService;
import com.app.ui.user_data.UserDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import persistence.converters.impl.CustomerJsonConverter;
import persistence.models.Customer;
import persistence.models.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MenuService {

    private final OrdersService ORDERS_SERVICE;

    public void mainMenu() {
        while (true) {
            try {
                System.out.println("-----[MENU]-----");
                System.out.println("0. Exit");
                System.out.println("1. Show all orders");
                System.out.println("2. Show most expensive product in each category");
                System.out.println("3. Show product for customer");
                System.out.println("4. Date with the lowest number of orders");
                System.out.println("5. Date with the biggest number of orders");
                System.out.println("6. Which customer paid the most");
                System.out.println("7. Category which products are bought most often");
                System.out.println("8. Month and number of bought products");
                System.out.println("9. Most popular product in Month");
                System.out.println("10. Customers who bought at least x pieces of the product");
                System.out.println("11. Count average price in time range");
                System.out.println("12. Count total price with discounts");

                int option = UserDataService.getInteger("Input option");
                switch (option) {
                    case 0 -> {
                        System.out.println("Goodbye");
                        return;
                    }
                    case 1 -> option1();
                    case 2 -> option2();
                    case 3 -> option3();
                    case 4 -> option4();
                    case 5 -> option5();
                    case 6 -> option6();
                    case 7 -> option7();
                    case 8 -> option8();
                    case 9 -> option9();
                    case 10 -> option10();
                    case 11 -> option11();
                    case 12 -> option12();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void option12() {
        System.out.println(ORDERS_SERVICE.countTotalPriceWithDiscounts());
    }

    private void option11() {
        LocalDate from = UserDataService.getLocalDate("Type date from");
        LocalDate to = UserDataService.getLocalDate("Type date to");
        System.out.println(ORDERS_SERVICE.averagePriceInTimeRange(from, to));
    }

    private void option10() {
        List<Customer> customers = ORDERS_SERVICE.findCustomersByNumberOfOrders(UserDataService.getInteger("Type quantity"));
        System.out.println(customers);
        final String FILENAME = "./resources/data/customersAndPurchases.json";
        new CustomerJsonConverter(FILENAME).toJson(customers);
    }

    private void option9() {
        System.out.println(toJson(ORDERS_SERVICE.mostPopularCategoryInMonth()));
    }

    private void option8() {
        System.out.println(toJson(ORDERS_SERVICE.monthAndOrderedProducts()));
    }

    private void option7() {
        System.out.println(toJson(ORDERS_SERVICE.categoryWhichProductsBuyTheMost()));
    }

    private void option6() {
        System.out.println(toJson(ORDERS_SERVICE.spentTheMost()));
    }

    private void option5() {
        System.out.println(toJson(ORDERS_SERVICE.dateWithMostOrders()));
    }

    private void option4() {
        System.out.println(toJson(ORDERS_SERVICE.dateWithLeastOrders()));
    }

    private void option3() {
        String email = UserDataService.getEmail("Input customer e-mail");
        List<Product> products = ORDERS_SERVICE.groupProductByCustomer(email);
        if (products.isEmpty()) {
            System.out.println("No products for this email");
            return;
        }
        System.out.println(toJson(products));
        EmailService.send(email, "Your products!", formatProductsList(products));
    }

    private String formatProductsList(List<Product> products) {
        return products
                .stream()
                .map(prod -> prod.getName() + " (" + prod.getCategory() + ") - " + prod.getPrice())
                .collect(Collectors.joining(", "));
    }

    private void option2() {
        System.out.println(toJson(ORDERS_SERVICE.findMostExpensiveProductInCategory()));
    }

    private void option1() {
        System.out.println(toJson(ORDERS_SERVICE.showAllOrders()));
    }

    private static <T> String toJson(T item) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(item);
    }

}
