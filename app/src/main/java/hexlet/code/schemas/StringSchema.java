package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema extends BaseSchema<String> {
    private int minLength = -1;
    private List<String> contains = new ArrayList<>();

    public StringSchema minLength(int length) {
        minLength = length;
        addValidation("minLength", value -> value != null && value.length() >= minLength);
        return this;
    }

    public StringSchema contains(String subString) {
        contains.add(subString);
        addValidation("contains", value -> value != null && value.contains(subString));
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (!super.isValid(value)) {
            return false;
        }

        for (String subString : contains) {
            if (value != null && !value.contains(subString)) {
                return false;
            }
        }
        return true;
    }
}
