package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema {
    private boolean isRequired = false;
    private int minLength = -1;
    private List<String> contains = new ArrayList<>();

    public StringSchema required() {
        isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String subString) {
        contains.add(subString);
        return this;
    }

    public boolean isValid(String value) {
        if (isRequired && (value == null || value.isEmpty())) {
            return false;
        }
        if (minLength != -1 && (value == null || value.length() < minLength)) {
            return false;
        }
        for (String substring : contains) {
            if (value != null && !value.contains(substring)) {
                return false;
            }
        }
        return true;
    }
}
