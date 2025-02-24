package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> validations = new HashMap<>();

    protected void addValidation(String name, Predicate<T> validation) {
        validations.put(name, validation);
    }

    public boolean isValid(T value) {
        for (Predicate<T> validator : validations.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }
}
