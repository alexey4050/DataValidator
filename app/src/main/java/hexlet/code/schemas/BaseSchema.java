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
        return validations.values().stream().allMatch(check -> check.test(value));
    }

    public abstract BaseSchema<T> required();
}
