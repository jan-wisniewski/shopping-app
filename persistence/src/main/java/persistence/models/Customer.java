package persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer implements Cloneable {
    private String name;
    private String surname;
    private int age;
    private String email;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
