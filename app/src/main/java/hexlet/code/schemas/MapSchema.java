package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Integer sizeLimit = null;

    public MapSchema() {
        addValidation("required", this::isRequired);
        addValidation("sizeLimit", this::isSizeValid);
    }

    private boolean isRequired(Map<?, ?> value) {
        return !isRequired || value != null;
    }

    private boolean isSizeValid(Map<?, ?> value) {
        if (sizeLimit != null) {
            return value != null && value.size() == sizeLimit;
        }
        return true;
    }

    @Override
    public boolean isValid(Map<?, ?> value) {
        for (Predicate<Map<?, ?>> validator : validations.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }

    public MapSchema required() {
        isRequired = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.sizeLimit = size;
        return this;
    }
}
