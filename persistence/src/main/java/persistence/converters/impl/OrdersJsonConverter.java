package persistence.converters.impl;

import persistence.converters.generic.JsonConverter;
import persistence.models.Order;

import java.util.List;

public class OrdersJsonConverter extends JsonConverter<List<Order>> {
    public OrdersJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
