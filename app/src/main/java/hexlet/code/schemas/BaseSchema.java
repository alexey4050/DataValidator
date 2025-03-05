package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> validations = new HashMap<>();

    protected final void addValidation(String name, Predicate<T> validation) {
        validations.put(name, validation);
    }

    protected final boolean isNullAllowed(T value) {
        return value == null;
    }

    public final boolean isValid(T value) {
        return validations.values().stream().allMatch(check -> check.test(value));
    }

    public abstract BaseSchema<T> required();
}
