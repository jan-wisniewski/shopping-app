package persistence.converters.impl;

import persistence.converters.generic.JsonConverter;
import persistence.models.Product;

import java.util.List;

public class ProductsJsonConverter extends JsonConverter<List<Product>> {
    public ProductsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
