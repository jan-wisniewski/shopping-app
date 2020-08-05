package com.app.ui.user_data;

import com.app.service.exceptions.ServiceException;
import com.app.ui.exceptions.UserDataException;

import java.time.LocalDate;
import java.util.Scanner;

public final class UserDataService {
    private UserDataService() {
    }

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getEmail (String message){
        System.out.println(message);
        String value = SCANNER.nextLine();
        if (!value.matches("([a-zA-z0-9]+?.)+@[a-z0-9]+\\.(pl|com.pl|com)")){
            throw new UserDataException("Incorrect email");
        }
        return value;
    }

    public static int getInteger(String message) {
        System.out.println(message);
        String value = SCANNER.nextLine();
        if (!value.matches("\\d+")) {
            throw new UserDataException("Input is not a correct value");
        }
        return Integer.parseInt(value);
    }


    public static LocalDate getLocalDate(String message) {
        System.out.println(message);
        System.out.println("HINT: Date format: RRRR-MM-DD");
        String input = SCANNER.nextLine();
        if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new UserDataException("Input is not valid local date");
        }
        if (!isValidMonthAndDay(input)) {
            throw new UserDataException("Incorrect values for date");
        }
        return LocalDate.parse(input);
    }

    public static boolean isValidMonthAndDay(String date) {
        if (date == null) {
            throw new ServiceException("Date is null");
        }
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        if (Integer.parseInt(month) < 0 || Integer.parseInt(month) > 12) {
            return false;
        }
        return Integer.parseInt(day) >= 0 && Integer.parseInt(day) <= 31;
    }

}


