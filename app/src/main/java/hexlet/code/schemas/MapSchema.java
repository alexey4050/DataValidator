package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private Integer sizeLimit = null;
    private Map<String, BaseSchema<V>> shapeSchemas = null;

    public MapSchema() {
        addValidation("required", this::isRequired);
        addValidation("sizeLimit", this::isSizeValid);
    }

    private boolean isRequired(Map<K, V> value) {
        return !isRequired || value != null;
    }

    private boolean isSizeValid(Map<K, V> value) {
        if (sizeLimit != null) {
            return value != null && value.size() == sizeLimit;
        }
        return true;
    }

    @Override
    public boolean isValid(Map<K, V> value) {
        for (Predicate<Map<K, V>> validator : validations.values()) {
            if (!validator.test(value)) {
                return false;
            }
        }
        return true;
    }

    public MapSchema<K, V> required() {
        isRequired = true;
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        this.sizeLimit = size;
        return this;
    }

    public MapSchema<K, V> shape(Map<String, BaseSchema<V>> schemas) {
        this.shapeSchemas = schemas;
        addValidation("shape", value ->
                value == null || shapeSchemas.entrySet().stream().allMatch(e -> {
                    String key = e.getKey();
                    BaseSchema<V> schema = e.getValue();
                    V propertyValue = value.get(key);
                    return schema.isValid(propertyValue);
                })
        );
        return this;
    }
}
