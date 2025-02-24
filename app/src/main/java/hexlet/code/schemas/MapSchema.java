package hexlet.code.schemas;

import java.util.Map;

public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private Integer sizeLimit = null;
    private Map<String, BaseSchema<V>> shapeSchemas = null;

    public MapSchema() {
    }

    @Override
    public boolean isValid(Map<K, V> value) {
        boolean isValid = super.isValid(value);
        if (!isValid) {
            return false;
        }

        if (sizeLimit != null && (value == null || value.size() != sizeLimit)) {
            return false;
        }

        if (shapeSchemas != null) {
            for (Map.Entry<String, BaseSchema<V>> entry : shapeSchemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<V> schema = entry.getValue();
                V propertyValue = (value != null) ? value.get(key) : null;
                if (!schema.isValid(propertyValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    public MapSchema<K, V> required() {
        addValidation("required", value -> value != null);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        this.sizeLimit = size;
        return this;
    }

    public MapSchema<K, V> shape(Map<String, BaseSchema<V>> schemas) {
        this.shapeSchemas = schemas;
        return this;
    }
}
