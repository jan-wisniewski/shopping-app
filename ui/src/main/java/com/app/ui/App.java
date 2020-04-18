package com.app.ui;

import com.app.service.data.DataGenerator;
import com.app.service.service.OrdersService;
import com.app.ui.menu.MenuService;

public class App {
    public static void main(String[] args) {
        try {
            final String FILENAME = "./resources/data/orders.json";
            final int CUSTOMERS_NUMBER = 20;
            final int PRODUCTS_NUMBER = 150;
            final int ORDERS_NUMBER = 30;
            DataGenerator.saveToJsonFile(CUSTOMERS_NUMBER,PRODUCTS_NUMBER,ORDERS_NUMBER);
            OrdersService ordersService = new OrdersService(FILENAME);
            MenuService menuService = new MenuService(ordersService);
            menuService.mainMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
