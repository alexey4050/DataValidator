package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public NumberSchema required() {
        addValidation("required", value -> !isNullAllowed(value));
        return this;
    }

    public NumberSchema positive() {
        addValidation("positive", value -> isNullAllowed(value) || value > 0);
        return this;
    }

    public NumberSchema range(int startWithNumber, int endWithNumber) {
        addValidation("range", value -> isNullAllowed(value) || value >= startWithNumber
                && value <= endWithNumber);
        return this;
    }
}
