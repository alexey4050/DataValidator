package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected boolean isRequired = false;
    protected Map<String, Predicate<T>> validations = new HashMap<>();

    public BaseSchema<T> required() {
        isRequired = true;
        return this;
    }

    public boolean isValid(T value) {
        if (isRequired && value == null) {
            return false;
        }
        for (Predicate<T> validator : validations.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }

    protected void addValidation(String name, Predicate<T> validation) {
        validations.put(name, validation);
    }
}
