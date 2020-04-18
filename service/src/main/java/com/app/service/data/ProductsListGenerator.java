package com.app.service.data;

import com.app.service.data.generic.Generator;
import persistence.enums.Category;
import persistence.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ProductsListGenerator implements Generator<List<Product>> {

    final String[] products = {
            "PRALKA", "LODOWKA", "LAPTOP", "DONICZKA", "LAMPA", "BIURKO", "KOMPUTER"
    };

    private static final Random random = new Random();


    @Override
    public List<Product> generate(int size) {
        List<Product> prods = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            prods.add(Product.builder()
                    .name(products[random.nextInt(products.length)])
                    .category(Category.values()[random.nextInt(Category.values().length)])
                    .price(BigDecimal.valueOf(random.nextInt(4000) + 1))
                    .build());
        }
        return prods;
    }
}
