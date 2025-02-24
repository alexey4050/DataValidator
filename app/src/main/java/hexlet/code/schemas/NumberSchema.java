package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Number> {
    public NumberSchema positive() {
        addValidation("positive", value -> value != null && value.doubleValue() > 0);
        return this;
    }
    public NumberSchema range(double min, double max) {
        addValidation("range", value -> value != null && value.doubleValue() >= min
                && value.doubleValue() <= max);
        return this;
    }
}
