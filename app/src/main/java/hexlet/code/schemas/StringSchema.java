package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema extends BaseSchema<String> {
    private int minLength = -1;
    private List<String> contains = new ArrayList<>();

    public StringSchema required() {
        addValidation("required", value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Минимальная длина не может быть отрицательной");
        }
        this.minLength = length;
        addValidation("minLength", value -> value != null
                && value.length() >= minLength);
        return this;
    }

    public StringSchema contains(String subString) {
        this.contains.add(subString);
        addValidation("contains_" + subString, value -> value != null
                && value.contains(subString));
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (!super.isValid(value)) {
            return false;
        }
        if (minLength != -1 && value.length() < minLength) {
            return false;
        }

        for (String subString : contains) {
            if (value == null || !value.contains(subString)) {
                return false;
            }
        }
        return true;
    }
}
