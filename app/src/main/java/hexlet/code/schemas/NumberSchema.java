package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public BaseSchema<Integer> required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidation("positive", value -> value == null || value > 0);
        return this;
    }

    public NumberSchema range(int startWithNumber, int endWithNumber) {
        addValidation("range", value -> value == null || value >= startWithNumber
                && value <= endWithNumber);
        return this;
    }
}
