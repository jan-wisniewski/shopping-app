package persistence.converters.impl;

import persistence.converters.generic.JsonConverter;
import persistence.models.Customer;

import java.util.List;

public class CustomerJsonConverter extends JsonConverter<List<Customer>> {
    public CustomerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
