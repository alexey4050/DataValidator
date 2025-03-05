package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addValidation("required", value -> !isNullAllowed(value) && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addValidation("minLength", value -> isNullAllowed(value)
                || value.length() >= length);
        return this;
    }

    public StringSchema contains(String subString) {
        addValidation("contains", value -> isNullAllowed(value)
                || value.contains(subString));
        return this;
    }
}
