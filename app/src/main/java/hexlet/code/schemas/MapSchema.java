package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    @Override
    public BaseSchema<Map<?, ?>> required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> value ==  null || value.size() == size);
        return this;
    }
    @SuppressWarnings("unchecked")
    public <K, V> MapSchema shape(Map<K, BaseSchema<V>> schemas) {
        addValidation("shape", map -> map != null && schemas.entrySet().stream()
                .allMatch(schema -> schema.getValue().isValid((V) map.get(schema.getKey()))));
        return this;
    }
}
