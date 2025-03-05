package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    @Override
    public BaseSchema<Map<?, ?>> required() {
        addValidation("required", value -> !isNullAllowed(value));
        return this;
    }

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> isNullAllowed(value) || value.size() == size);
        return this;
    }
    @SuppressWarnings("unchecked")
    public <K, V> MapSchema shape(Map<K, BaseSchema<V>> schemas) {
        addValidation("shape", map -> !isNullAllowed(schemas) && schemas.entrySet().stream()
                .allMatch(schema -> schema.getValue().isValid((V) map.get(schema.getKey()))));
        return this;
    }
}
