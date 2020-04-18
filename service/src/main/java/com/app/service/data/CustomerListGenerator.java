package com.app.service.data;

import com.app.service.data.generic.Generator;
import persistence.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CustomerListGenerator implements Generator<List<Customer>> {

    final static String[] names = {
            "JAN", "OLA", "ADAM", "EWA", "ROBERT", "JUSTYNA"
    };
    final static String[] surnames = {
            "NOWAK", "ADAMIEC", "WISNIEWSKI", "KOWALSKI", "MALINOWSKI", "ZURAWSKI"
    };

    private static final Random random = new Random();


    @Override
    public List<Customer> generate(int size) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            customers.add(
                    Customer.builder()
                            .name(names[random.nextInt(names.length)])
                            .surname(surnames[random.nextInt(surnames.length)])
                            .age(random.nextInt(50) + 1)
                            .email(generateRandomEmail().concat("@mail.pl"))
                            .build()
            );
        }
        return customers;
    }

    private String generateRandomEmail() {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int length = random.nextInt(5) + 3;
        return Stream.generate(() -> letters[random.nextInt(letters.length - 1)])
                .limit(length)
                .map(Object::toString)
                .reduce(String::concat)
                .orElseThrow();
    }
}
